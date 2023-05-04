package project.travelmate.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.ProfileImage;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.Gender;
import project.travelmate.repository.UserRepository;
import project.travelmate.response.ProfileResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    UserRepository userRepository;

    @Nested
    class getProfile {

        @Test
        void success() {
            ProfileImage profileImage = ProfileImage.builder().filePath("path").build();
            User user = User.builder()
                    .id("user1_id").email("email").name("user1").gender(Gender.MALE).intro("intro").profileImage(profileImage)
                    .build();
            Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

            ProfileResponse userResponse = profileService.getProfile("user1_id");

            assertThat(userResponse.getId()).isEqualTo(user.getId());
            assertThat(userResponse.getName()).isEqualTo(user.getName());
            assertThat(userResponse.getProfileUrl()).isEqualTo(user.getProfileImage().getFilePath());
            assertThat(userResponse.getIntro()).isEqualTo(user.getIntro());
            assertThat(userResponse.getGender()).isEqualTo(user.getGender());
        }

        @Test
        void userNotFound() {
            Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> profileService.getProfile("user1_id"))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @Test
        void profileImageNotExist() {
            User user = User.builder()
                    .id("user1_id").email("email").name("user1").gender(Gender.MALE).intro("intro")
                    .build();
            Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

            ProfileResponse userResponse = profileService.getProfile("user1_id");

            assertThat(userResponse.getProfileUrl()).isEqualTo(null);
        }
    }



}