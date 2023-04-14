package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.base.TimeEntity;
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

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String name;

    private Integer mannerTemperature;

    private String intro;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="profile_image_id")
    private ProfileImage profileImage;

}