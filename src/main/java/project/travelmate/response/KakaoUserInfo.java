package project.travelmate.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfo {

    private String id;
    private KakaoAccount kakaoAccount;

    @Getter
    private static class KakaoAccount {
        private String email;
        private Profile profile;

        @Getter
        private static class Profile {
            private String nickname;
        }
    }

}