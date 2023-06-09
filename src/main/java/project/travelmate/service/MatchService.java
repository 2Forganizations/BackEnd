package project.travelmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.travelmate.advice.exception.AuthorityException;
import project.travelmate.advice.exception.DuplicateRequestException;
import project.travelmate.advice.exception.PlanNotFoundException;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.domain.PlanMember;
import project.travelmate.domain.User;
import project.travelmate.domain.WaitMember;
import project.travelmate.domain.enums.Role;
import project.travelmate.repository.PlanMemberRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.repository.WaitMemberRepository;
import project.travelmate.repository.PlanRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final WaitMemberRepository waitMemberRepository;
    private final PlanMemberRepository planMemberRepository;

    @Transactional
    public void createWaitMember(String memberId, Long planId) {
        Plan plan = planRepository.findPlanWithPlanMembersAndWaitMembersById(planId)
                .orElseThrow(() -> new PlanNotFoundException());
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException());

        isOwner(plan, memberId);
        isWaitMember(plan, memberId);

        WaitMember waitMember = new WaitMember(plan, user);
        waitMemberRepository.save(waitMember);
    }

    @Transactional
    public void acceptWaitMember(String memberId, Long waitMemberId) {
        WaitMember waitMember = waitMemberRepository.findById(waitMemberId)
                .orElseThrow(() -> new UserNotFoundException());
        Plan plan = waitMember.getPlan();
        isOwner(plan, memberId);

        PlanMember planMember = new PlanMember(Role.MEMBER, plan, waitMember.getUser());
        plan.getPlanMembers().add(planMember);

        waitMemberRepository.deleteById(waitMemberId);
    }

    @Transactional
    public void deleteWaitMember(String memberId, Long waitMemberId) {
        WaitMember waitMember = waitMemberRepository.findById(waitMemberId)
                .orElseThrow(() -> new UserNotFoundException());
        Plan plan = waitMember.getPlan();
        isOwner(plan, memberId);

        waitMemberRepository.deleteById(waitMemberId);
    }

    @Transactional
    public void banMember(String memberId, Long planMemberId) {
        PlanMember planMember = planMemberRepository.findById(planMemberId)
                .orElseThrow(() -> new UserNotFoundException());
        Plan plan = planMember.getPlan();
        isOwner(plan, memberId);

        planMemberRepository.deleteById(planMemberId);
    }

    private void isWaitMember(Plan plan, String memberId) {
        for (WaitMember waitMember : plan.getWaitMembers()) {
            if (memberId.equals(waitMember.getUser().getId())) {
                throw new DuplicateRequestException();
            }
        }
    }

    private void isOwner(Plan plan, String memberId) {
        for (PlanMember planMember : plan.getPlanMembers()) {
            if (planMember.getRole().equals(Role.OWNER) && planMember.getUser().getId().equals(memberId)) {
                throw new AuthorityException();
            }
        }
    }



}
