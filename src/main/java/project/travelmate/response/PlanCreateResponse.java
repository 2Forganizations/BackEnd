package project.travelmate.response;

import lombok.Getter;
import project.travelmate.domain.Plan;
import project.travelmate.domain.PlanImage;

@Getter
public class PlanCreateResponse {

    private Long planId;
    private String thumbnailUrl;

    public PlanCreateResponse(Plan plan, PlanImage planImage) {
        this.planId = plan.getId();
        this.thumbnailUrl = planImage.getFilePath();
    }

}