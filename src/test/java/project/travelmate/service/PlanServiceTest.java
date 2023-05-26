package project.travelmate.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import project.travelmate.advice.exception.PlanNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.repository.PlanRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.plan.CardPlanResponse;
import project.travelmate.response.plan.PlanCreateResponse;
import project.travelmate.response.plan.PlanDetailResponse;


import java.util.List;
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

    @Nested
    class getPlanList {
        @Test
        public void plan이_없을때() {
            Mockito.when(planRepository.findPlansWithPlanOwner(ArgumentMatchers.any(Pageable.class)))
                    .thenReturn(new SliceImpl<Plan>(List.of()));

            Slice<CardPlanResponse> planList = planService.getPlanList(Pageable.ofSize(3));

            assertThat(planList.getContent().size()).isEqualTo(0);
        }

        @Test
        public void 여러개_있을때() {
            Plan plan1 = makeDummyPlan();
            Plan plan2 = makeDummyPlan();
            Plan plan3 = makeDummyPlan();
            Mockito.when(planRepository.findPlansWithPlanOwner(ArgumentMatchers.any(Pageable.class)))
                    .thenReturn(new SliceImpl<Plan>(List.of(plan1, plan2, plan3)));

            Slice<CardPlanResponse> planList = planService.getPlanList(Pageable.ofSize(2));
            List<CardPlanResponse> content = planList.getContent();

            assertThat(content.size()).isEqualTo(3);
        }


    }



}