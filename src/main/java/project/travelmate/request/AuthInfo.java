package project.travelmate.request;

import lombok.Getter;

@Getter
public class AuthInfo {

    private String id;
    private String authProvider;
    private String accessToken;

    public AuthInfo(String id, String authProvider, String accessToken) {
        this.id = id;
        this.authProvider = authProvider;
        this.accessToken = accessToken;
    }

}