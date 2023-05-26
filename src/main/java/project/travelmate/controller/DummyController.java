package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.travelmate.domain.Plan;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.AuthProvider;
import project.travelmate.domain.enums.Category;
import project.travelmate.domain.enums.Gender;
import project.travelmate.repository.PlanRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.PlanCreateRequest;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyController {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @PostConstruct
    public void init() {
        User user1 = new User("9999", "", "최기나", 1, "안녕하세요",
                Gender.FEMALE, AuthProvider.KAKAO, null, null);
        User user2 = new User("10000", "", "차라니", 1, "안녕하세요",
                    Gender.MALE, AuthProvider.KAKAO, null, null);
        User user3 = new User("10001", "", "아너로", 1, "안녕하세요",
                    Gender.MALE, AuthProvider.KAKAO, null, null);

        userRepository.saveAll(List.of(user1, user2, user3));

        PlanCreateRequest planCreateRequest1 = new PlanCreateRequest("산책 가실분", "이야기......", Category.WALK, LocalDateTime.now(), LocalDateTime.now(),
                20, 40, "대한민국", "서울", "도로명...", "123", "456", 1, 2, 3);
        PlanCreateRequest planCreateRequest2 = new PlanCreateRequest("여행 가실분", "이야기......", Category.TOUR, LocalDateTime.now(), LocalDateTime.now(),
                30, 40, "대한민국", "서울", "도로명...", "123", "456", 1, 2, 3);
        PlanCreateRequest planCreateRequest3 = new PlanCreateRequest("식사 하실분", "이야기......", Category.EAT, LocalDateTime.now(), LocalDateTime.now(),
                50, 50, "대한민국", "서울", "도로명...", "123", "456", 1, 2, 3);
        Plan plan1 = Plan.createPlan(planCreateRequest1, "9999");
        Plan plan2 = Plan.createPlan(planCreateRequest2, "10000");
        Plan plan3 = Plan.createPlan(planCreateRequest3, "10001");
        planRepository.saveAll(List.of(plan1, plan2, plan3));

    }
}
