package project.travelmate.response;

import lombok.Getter;

@Getter
public class PlanCreateResponse {

    private Long planId;
    private String thumbnailUrl;

    public PlanCreateResponse(Long planId, String thumbnailUrl) {
        this.planId = planId;
        this.thumbnailUrl = thumbnailUrl;
    }

}