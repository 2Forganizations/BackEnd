package project.travelmate.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRequest {

    private String registrationId;
    private String code;
    private String refreshToken;

    public TokenRequest(String registrationId, String code) {
        this.registrationId = registrationId;
        this.code = code;
    }

}