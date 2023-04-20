package project.travelmate.service;

import project.travelmate.request.TokenRequest;
import project.travelmate.response.SignInResponse;
import project.travelmate.response.TokenResponse;

public interface RequestService<T> {

    SignInResponse redirect(TokenRequest tokenRequest);

    TokenResponse getToken(TokenRequest tokenRequest);

    T getUserInfo(String accessToken);

    TokenResponse getRefreshToken(String provider, String refreshToken);

}