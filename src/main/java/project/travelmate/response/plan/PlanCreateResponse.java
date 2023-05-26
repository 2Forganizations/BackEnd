package project.travelmate.response.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.travelmate.domain.Plan;

@AllArgsConstructor
@Getter
public class PlanCreateResponse {
    private Long planId;

    public PlanCreateResponse(Plan plan) {
        this.planId = plan.getId();
    }
}
