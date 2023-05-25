package project.travelmate.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.travelmate.advice.exception.PlanNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.repository.PlanRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;
import project.travelmate.response.PlanDetailResponse;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.travelmate.dummy.DummyObjects.makeDummyPlan;
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

    @Nested
    class getPlanDetail {
        @Test
        public void 성공() {
            Plan plan = makeDummyPlan();
            Mockito.when(planRepository.findPlanWithPlanMembersById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(plan));

            PlanDetailResponse planDetail = planService.getPlanDetail(1L);


            assertThat(planDetail.getAddress()).usingRecursiveComparison().isEqualTo(plan.getAddress());

            assertThat(planDetail.getRecruitNumber().getRecruitManNumber())
                    .isEqualTo(Integer.toString(Integer.parseInt(plan.getRequireRecruitMember().split("/")[0])
                            - Integer.parseInt(plan.getCurrentRecruitMember().split("/")[0]))
                            + "/" + plan.getRequireRecruitMember().split("/")[0]);
            assertThat(planDetail.getRecruitNumber().getRecruitWomanNumber())
                    .isEqualTo(Integer.toString(Integer.parseInt(plan.getRequireRecruitMember().split("/")[1])
                            - Integer.parseInt(plan.getCurrentRecruitMember().split("/")[1]))
                            + "/" + plan.getRequireRecruitMember().split("/")[1]);
            assertThat(planDetail.getRecruitNumber().getRecruitEtcNumber())
                    .isEqualTo(Integer.toString(Integer.parseInt(plan.getRequireRecruitMember().split("/")[2])
                            - Integer.parseInt(plan.getCurrentRecruitMember().split("/")[2]))
                            + "/" + plan.getRequireRecruitMember().split("/")[2]);
        }

        @Test
        public void Plan이_존재하지_않을때() {
            Mockito.when(planRepository.findPlanWithPlanMembersById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> planService.getPlanDetail(1L))
                    .isInstanceOf(PlanNotFoundException.class);

        }

        @Test
        @Disabled
        public void 멤버가_여러명일때() {
            return;
        }
    }


}