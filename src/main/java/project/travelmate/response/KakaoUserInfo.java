package project.travelmate.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserInfo {

    private String id;
    private String email;
    private String name;

    public KakaoUserInfo(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}