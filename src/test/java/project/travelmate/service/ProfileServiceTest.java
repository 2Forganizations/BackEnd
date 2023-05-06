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
import project.travelmate.request.MemberProfileUpdateRequest;
import project.travelmate.response.ProfileResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;


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
            User user = makeUser("path");
            Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

            ProfileResponse userResponse = profileService.getProfile("user_id");

            assertThat(userResponse.getId()).isEqualTo(user.getId());
            assertThat(userResponse.getName()).isEqualTo(user.getName());
            assertThat(userResponse.getProfileUrl()).isEqualTo(user.getProfileImage().getFilePath());
            assertThat(userResponse.getIntro()).isEqualTo(user.getIntro());
            assertThat(userResponse.getGender()).isEqualTo(user.getGender());
        }

        @Test
        void userNotFound() {
            Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> profileService.getProfile("user_id"))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @Test
        void profileImageNotExist() {
            User user = makeUser();
            Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

            ProfileResponse userResponse = profileService.getProfile("user_id");

            assertThat(userResponse.getProfileUrl()).isEqualTo(null);
        }

    }

    @Nested
    class EditProfile {
        @Test
        void success() {
            User user = makeUser();
            Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
            MemberProfileUpdateRequest memberProfileUpdateRequest = new MemberProfileUpdateRequest("edit_name", "edit_intro");

            profileService.editProfile("user_id", memberProfileUpdateRequest);

            assertThat(user.getName()).isEqualTo(memberProfileUpdateRequest.getName());
            assertThat(user.getIntro()).isEqualTo(memberProfileUpdateRequest.getIntro());
        }

    }



    private User makeUser() {
        return User.builder()
                .id("user_id").email("email").name("user1").gender(Gender.MALE).intro("intro")
                .build();
    }

    private User makeUser(String profileImagePath) {
        ProfileImage profileImage = ProfileImage.builder().filePath(profileImagePath).build();
        return User.builder()
                .id("user_id").email("email").name("user1").gender(Gender.MALE).intro("intro").profileImage(profileImage)
                .build();
    }
}