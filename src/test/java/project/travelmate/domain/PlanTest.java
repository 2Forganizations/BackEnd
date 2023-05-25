package project.travelmate.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.travelmate.domain.enums.Category;
import project.travelmate.request.PlanCreateRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static project.travelmate.dummy.DummyObjects.makePlanRequest;


class PlanTest {

    @Test
    public void createPlanTest() {
        PlanCreateRequest planCreateRequest = makePlanRequest();
        Plan plan = Plan.createPlan(planCreateRequest, "ownerId");

        assertThat(plan.getRequireRecruitMember())
                .isEqualTo(planCreateRequest.getRecruitManNumber() + "/"+
                                planCreateRequest.getRecruitWomanNumber() + "/" +
                                planCreateRequest.getRecruitEtcNumber());
        assertThat(plan.getCurrentRecruitMember())
                .isEqualTo(planCreateRequest.getRecruitManNumber() + "/"+
                        planCreateRequest.getRecruitWomanNumber() + "/" +
                        planCreateRequest.getRecruitEtcNumber());
        assertThat(plan.getPlanMembers().size()).isEqualTo(1);
    }


}