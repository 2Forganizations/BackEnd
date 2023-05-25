package project.travelmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.travelmate.domain.Plan;
import project.travelmate.repository.PlanRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;

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
}
