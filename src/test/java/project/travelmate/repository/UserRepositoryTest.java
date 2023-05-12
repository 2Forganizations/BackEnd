package project.travelmate.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.ProfileImage;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.Gender;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired UserRepository userRepository;


    @Test
    @DisplayName("findUserWithProfileImageById 확인")
    void findUserWithProfileImageById() {
        User user = makeUser("path");
        userRepository.save(user);

        User findUser = userRepository.findUserWithProfileImageById("user_id")
                .orElseThrow(UserNotFoundException::new);

        assertThat(user.getName()).isEqualTo(findUser.getName());
        assertThat(user.getGender()).isEqualTo(findUser.getGender());
        assertThat(user.getProfileImage().getFilePath()).isEqualTo(findUser.getProfileImage().getFilePath());
    }

    private User makeUser(String profileImagePath) {
        ProfileImage profileImage = ProfileImage.builder().filePath(profileImagePath).build();
        return User.builder()
                .id("user_id").email("email").name("user1").gender(Gender.MALE).intro("intro").profileImage(profileImage)
                .build();
    }
}