package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.base.TimeEntity;
import project.travelmate.domain.enums.AuthProvider;
import project.travelmate.domain.enums.Gender;
import project.travelmate.request.MemberProfileUpdateRequest;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends TimeEntity {

    @Id
    @Column(name = "user_id")
    private String id;

    private String email;
    private String name;
    private Integer mannerTemperature;
    private String intro;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    @Nullable
    private ProfileImage profileImage;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @Builder
    public User(String id, String email, String name, Integer mannerTemperature, String intro, Gender gender, AuthProvider authProvider, ProfileImage profileImage, RefreshToken refreshToken) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.mannerTemperature = mannerTemperature;
        this.intro = intro;
        this.gender = gender;
        this.authProvider = authProvider;
        this.profileImage = profileImage;
        this.refreshToken = refreshToken;
    }

    public void edit(MemberProfileUpdateRequest memberProfileUpdateRequest) {
        this.name = memberProfileUpdateRequest.getName();
        this.intro = memberProfileUpdateRequest.getIntro();
    }

    public void editProfileImagePath(String filePath) {
        if (this.profileImage == null) {
            this.profileImage = new ProfileImage(filePath);
        } else {
            this.profileImage.edit(filePath);
        }

    }
}