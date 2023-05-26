package project.travelmate.dummy;

import project.travelmate.domain.Plan;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.Category;
import project.travelmate.domain.enums.Gender;
import project.travelmate.request.PlanCreateRequest;

import java.time.LocalDateTime;

public class DummyObjects {

    public static PlanCreateRequest makePlanRequest() {
        return new PlanCreateRequest("title", "content", Category.WALK,
                LocalDateTime.now(), LocalDateTime.now(), 10, 20, "korea", "seoul",
                "detail", "123", "456", 1, 2, 3);
    }

    public static Plan makeDummyPlan() {
        return Plan.createPlan(makePlanRequest(), "ownerId");
    }

    public static User makeDummyUser() {
        return User.builder()
                .id("user_id").email("email").name("user1").gender(Gender.MALE).intro("intro")
                .build();
    }
}
