package project.travelmate.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponse {

    private String tokenType;
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private Integer refreshTokenExpiresIn;
    private String error;
    private String errorDescription;

    public TokenResponse(String tokenType, String accessToken, Integer expiresIn, String refreshToken, Integer refreshTokenExpiresIn, String error, String errorDescription) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.error = error;
        this.errorDescription = errorDescription;
    }
}