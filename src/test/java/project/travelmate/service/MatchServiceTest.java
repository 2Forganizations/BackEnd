package project.travelmate.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.travelmate.advice.exception.AuthorityException;
import project.travelmate.advice.exception.PlanNotFoundException;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.domain.User;
import project.travelmate.domain.WaitMember;
import project.travelmate.repository.PlanRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.repository.WaitMemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static project.travelmate.dummy.DummyObjects.makeDummyPlan;
import static project.travelmate.dummy.DummyObjects.makeDummyUser;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {
    @InjectMocks private MatchService matchService;

    @Mock
    PlanRepository planRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    WaitMemberRepository waitMemberRepository;


    @Nested
    class CreateWaitMember {
        @Test
        void 성공() {
            User user = makeDummyUser();
            Plan plan = makeDummyPlan();

            Mockito.when(planRepository.findPlanWithPlanMembersAndWaitMembersById(anyLong()))
                    .thenReturn(Optional.of(plan));
            Mockito.when(userRepository.findById(anyString()))
                    .thenReturn(Optional.of(user));
            Mockito.when(waitMemberRepository.save(any(WaitMember.class))).thenReturn(null);

            matchService.createWaitMember("id", 3L);
        }

        @Test
        void Plan이_존재하지_않을때() {
            Mockito.when(planRepository.findPlanWithPlanMembersAndWaitMembersById(anyLong()))
                    .thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> matchService.createWaitMember("id", 3L))
                    .isInstanceOf(PlanNotFoundException.class);
        }

        @Test
        void User가_존재하지_않을때() {
            Plan plan = makeDummyPlan();
            Mockito.when(planRepository.findPlanWithPlanMembersAndWaitMembersById(anyLong()))
                    .thenReturn(Optional.of(plan));
            Mockito.when(userRepository.findById(anyString()))
                    .thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> matchService.createWaitMember("id", 3L))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @Test
        void Owner가_WaitMember를_신청할() {
            Plan plan = makeDummyPlan();
            User user = makeDummyUser();
            Mockito.when(planRepository.findPlanWithPlanMembersAndWaitMembersById(anyLong()))
                    .thenReturn(Optional.of(plan));
            Mockito.when(userRepository.findById(anyString()))
                    .thenReturn(Optional.of(user));

            Assertions.assertThatThrownBy(() -> matchService.createWaitMember("ownerId", 3L))
                    .isInstanceOf(AuthorityException.class);
        }
    }


}