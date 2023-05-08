package project.travelmate.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.travelmate.domain.Plan;
import project.travelmate.domain.enums.Category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlanRepositoryTest extends RepositoryTest {

    @DisplayName("여행 계획 생성 API - Repository")
    @Test
    void plan_save_repository() {
        Plan savedPlan = planRepository.save(plan);

        assertNotNull(savedPlan.getId());
        assertThat(savedPlan).isNotNull();
        assertThat(savedPlan.getCategory()).isEqualTo(Category.EAT);
    }

}