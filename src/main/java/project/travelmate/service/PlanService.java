package project.travelmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.travelmate.advice.exception.PlanNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.repository.PlanRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.plan.CardPlanResponse;
import project.travelmate.response.plan.PlanCreateResponse;
import project.travelmate.response.plan.PlanDetailResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

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
}
