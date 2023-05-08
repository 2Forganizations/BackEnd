package project.travelmate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import project.travelmate.response.PlanCreateResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static project.travelmate.utils.constant.Constants.FILE_PATH;

class PlanServiceTest extends ServiceTest {

    //FIXME 이미지 관련 테스트는 추후에 리팩토링
    @DisplayName("여행 계획을 작성한다.")
    @Test
    void createPlan() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        MockMultipartFile file = new MockMultipartFile(FILE_PATH, FILE_PATH, "image/jpeg", "test image".getBytes());
        PlanCreateResponse planCreateResponse = planService.create(user.getId(), file, planCreateRequest);

        assertAll(
                () -> assertNotNull(planCreateResponse),
                () -> assertThat(planCreateResponse.getPlanId()).isEqualTo(plan.getId())
        );
    }

}