package project.travelmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.travelmate.advice.exception.NotOwnerException;
import project.travelmate.advice.exception.PlanNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.repository.PlanRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.plan.CardPlanResponse;
import project.travelmate.response.plan.PlanCreateResponse;
import project.travelmate.response.plan.PlanDetailResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlanCreateResponse createPlan(String userId, PlanCreateRequest planCreateRequest) {
        Plan plan = Plan.createPlan(planCreateRequest, userId);
        planRepository.save(plan);

        // require create plan chatroom code
        return new PlanCreateResponse(plan);
    }

    public PlanDetailResponse getPlanDetail(Long planId) {
        Plan plan = planRepository.findPlanWithPlanMembersById(planId).orElseThrow(PlanNotFoundException::new);

        return new PlanDetailResponse(plan);
    }

    public Slice<CardPlanResponse> getPlanList(Pageable pageable) {
        return planRepository.findPlansWithPlanOwner(pageable).map(CardPlanResponse::new);

    }

    @Transactional
    public void deletePlan(String userId, Long planId) {
        Plan plan = planRepository.findPlanByPlanId(planId)
                .orElseThrow(() -> new PlanNotFoundException());
        if (isNotOwner(userId, plan)) {
            throw new NotOwnerException();
        }

        planRepository.delete(plan);
    }

    private static boolean isNotOwner(String userId, Plan plan) {
        return !userId.equals(plan.getPlanMembers().get(0).getUser().getId());
    }
}
