package project.travelmate.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.travelmate.domain.Plan;
import project.travelmate.domain.enums.Category;
import project.travelmate.repository.PlanRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static project.travelmate.dummy.DummyObjects.makePlanRequest;


@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @InjectMocks PlanService planService;

    @Mock
    PlanRepository planRepository;

    @Nested
    class CreatePlan {
        @Test
        public void 성공() {
            Mockito.when(planRepository.save(ArgumentMatchers.any(Plan.class))).thenReturn(null);

            PlanCreateRequest planCreateRequest = makePlanRequest();
            PlanCreateResponse planResponse = planService.createPlan("userId", planCreateRequest);
        }
    }

}