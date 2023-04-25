package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.base.TimeEntity;
import project.travelmate.domain.enums.AuthProvider;
import project.travelmate.domain.enums.Gender;

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

    @Column(length = 500)
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @Builder
    public User(String id, String email, String name, Integer mannerTemperature, String intro, Gender gender, AuthProvider authProvider, String refreshToken, ProfileImage profileImage) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.mannerTemperature = mannerTemperature;
        this.intro = intro;
        this.gender = gender;
        this.authProvider = authProvider;
        this.refreshToken = refreshToken;
        this.profileImage = profileImage;
    }

}