package project.travelmate.service;

import org.springframework.stereotype.Service;
import project.travelmate.advice.exception.OauthNotSupportException;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.TokenRequest;
import project.travelmate.response.SignInResponse;
import project.travelmate.response.TokenResponse;
import project.travelmate.util.SecurityUtil;

import static project.travelmate.advice.ExceptionCodeConst.OAUTH_NOT_SUPPORT_CODE;
import static project.travelmate.advice.ExceptionCodeConst.USER_NOT_FOUND_CODE;
import static project.travelmate.domain.enums.AuthProvider.KAKAO;
import static project.travelmate.domain.enums.AuthProvider.findByCode;

@Service
public class AuthService {

    private KakaoRequestService kakaoRequestService;
    private UserRepository userRepository;
    private SecurityUtil securityUtil;

    public AuthService(KakaoRequestService kakaoRequestService, UserRepository userRepository, SecurityUtil securityUtil) {
        this.kakaoRequestService = kakaoRequestService;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    public SignInResponse redirect(TokenRequest tokenRequest) {
        if (KAKAO.getAuthProvider().equals(tokenRequest.getRegistrationId())) {
            return kakaoRequestService.redirect(tokenRequest);
        }
        throw new OauthNotSupportException(OAUTH_NOT_SUPPORT_CODE);
    }

    public SignInResponse refreshToken(TokenRequest tokenRequest) {
        String userId = (String) securityUtil.get(tokenRequest.getRefreshToken()).get("userId");
        String provider = (String) securityUtil.get(tokenRequest.getRefreshToken()).get("provider");
        String oldRefreshToken = (String) securityUtil.get(tokenRequest.getRefreshToken()).get("refreshToken");

        userExistValidation(userId, provider);

        TokenResponse tokenResponse = null;
        if (KAKAO.getAuthProvider().equals(provider.toLowerCase())) {
            tokenResponse = kakaoRequestService.getRefreshToken(provider, oldRefreshToken);
        }
        String accessToken = securityUtil.createAccessToken(userId, findByCode(provider.toLowerCase()), tokenResponse.getAccess_token());
        SignInResponse signInResponse = new SignInResponse(findByCode(provider.toLowerCase()), null, accessToken, null);

        return signInResponse;
    }

    private void userExistValidation(String userId, String provider) {
        if (!userRepository.existsByIdAndAuthProvider(userId, findByCode(provider))) {
            throw new UserNotFoundException(USER_NOT_FOUND_CODE);
        }
    }

}