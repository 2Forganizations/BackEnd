package project.travelmate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.AuthProvider;
import project.travelmate.domain.enums.Gender;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.TokenRequest;
import project.travelmate.response.KakaoUserInfo;
import project.travelmate.response.SignInResponse;
import project.travelmate.response.TokenResponse;
import project.travelmate.util.SecurityUtil;

import java.util.Optional;

import static java.lang.String.valueOf;

@Service
public class KakaoRequestService implements RequestService {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${spring.security.oauth2.client.provider.kakao.token_uri}")
    private String TOKEN_URI;

    public KakaoRequestService(UserRepository userRepository, SecurityUtil securityUtil, WebClient webClient) {
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
        this.webClient = webClient;
    }

    @Override
    @Transactional
    public SignInResponse redirect(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = getToken(tokenRequest);
        KakaoUserInfo kakaoUserInfo = getUserInfo(tokenResponse.getAccess_token());

        String accessToken = securityUtil.createAccessToken(kakaoUserInfo.getId(),
                AuthProvider.KAKAO,
                tokenResponse.getAccess_token());
        String refreshToken = securityUtil.createRefreshToken(kakaoUserInfo.getId(),
                AuthProvider.KAKAO,
                tokenResponse.getRefresh_token());

        Optional<User> findUser = userRepository.findById(valueOf(kakaoUserInfo.getId()));
        if (findUser.isEmpty()) {
            User user = User.builder()
                    .id(kakaoUserInfo.getId())
                    .email(kakaoUserInfo.getKakaoAccount().getEmail())
                    .name(kakaoUserInfo.getKakaoAccount().getProfile().getNickname())
                    .gender(Gender.MALE)
                    .authProvider(AuthProvider.KAKAO)
                    .refreshToken(refreshToken)
                    .build();
            userRepository.save(user);
        }


        return new SignInResponse(AuthProvider.KAKAO, kakaoUserInfo, accessToken, refreshToken);
    }

    @Override
    public TokenResponse getToken(TokenRequest tokenRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GRANT_TYPE);
        formData.add("redirect_uri", REDIRECT_URI);
        formData.add("client_id", CLIENT_ID);
        formData.add("code", tokenRequest.getCode());

        return webClient
                .mutate()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(
                        HttpStatus.UNAUTHORIZED::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    @Override
    public KakaoUserInfo getUserInfo(String accessToken) {
        return webClient
                .mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(
                        HttpStatus.UNAUTHORIZED::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }

    @Override
    public TokenResponse getRefreshToken(String provider, String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", CLIENT_ID);
        formData.add("refresh_token", refreshToken);

        return webClient
                .mutate()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }

}