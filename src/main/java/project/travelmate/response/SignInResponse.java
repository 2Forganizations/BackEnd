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

    public SignInResponse(AuthProvider authProvider, KakaoUserInfo kakaoUserInfo) {
        this.authProvider = authProvider;
        this.kakaoUserInfo = kakaoUserInfo;
    }

    public SignInResponse(AuthProvider authProvider, KakaoUserInfo kakaoUserInfo, String accessToken, String refreshToken) {
        this.authProvider = authProvider;
        this.kakaoUserInfo = kakaoUserInfo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}