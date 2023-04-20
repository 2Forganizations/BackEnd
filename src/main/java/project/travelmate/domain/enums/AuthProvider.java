package project.travelmate.domain.enums;

import java.util.Arrays;

public enum AuthProvider {

    KAKAO("kakao"), EMPTY("");

    private String authProvider;

    AuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public static AuthProvider findByCode(String code) {
        return Arrays.stream(AuthProvider.values())
                .filter(provider -> provider.getAuthProvider().equals(code))
                .findFirst()
                .orElse(EMPTY);
    }

    public String getAuthProvider() {
        return authProvider;
    }

}