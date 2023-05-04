package project.travelmate.response;

import lombok.Getter;
import project.travelmate.domain.ProfileImage;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.Gender;

import java.util.Optional;

@Getter
public class ProfileResponse {

    private String id;
    private String name;
    private Gender gender;
    private String intro;
    private String profileUrl;

    public ProfileResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.intro = user.getIntro();
        Optional<ProfileImage> profileImage = Optional.ofNullable(user.getProfileImage());
        this.profileUrl = profileImage.map(p -> p.getFilePath()).orElse(null);

    }
}
