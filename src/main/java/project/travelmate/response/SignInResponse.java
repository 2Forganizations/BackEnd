package project.travelmate.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.enums.AuthProvider;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInResponse {

    private AuthProvider authProvider;
    private KakaoUserInfo kakaoUserInfo;
    private String accessToken;
    private String refreshToken;
    private boolean isFirst;

    public SignInResponse(AuthProvider authProvider, KakaoUserInfo kakaoUserInfo, String accessToken, String refreshToken, boolean isFirst) {
        this.authProvider = authProvider;
        this.kakaoUserInfo = kakaoUserInfo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isFirst = isFirst;
    }
}