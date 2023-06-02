package project.travelmate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.travelmate.domain.Plan;
import project.travelmate.request.PlanSearchRequest;
import project.travelmate.response.plan.CardPlanResponse;

import java.util.List;

public interface PlanCustomRepository {
    Page<CardPlanResponse> search(PlanSearchRequest planSearchRequest, Pageable pageable);
}
