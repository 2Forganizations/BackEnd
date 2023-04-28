package project.travelmate.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.User;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.AuthInfo;
import project.travelmate.request.TokenRequest;
import project.travelmate.response.SignInResponse;
import project.travelmate.service.AuthService;

import static project.travelmate.advice.ExceptionCodeConst.USER_NOT_FOUND_CODE;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login/oauth2/{registrationId}")
    public ResponseEntity<SignInResponse> redirect(
            @PathVariable("registrationId") String registrationId,
            @RequestParam("code") String code) {
        SignInResponse response = authService.redirect(new TokenRequest(registrationId, code));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + response.getAccessToken());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(response);
    }

    @PostMapping("/auth/token")
    public ResponseEntity<SignInResponse> refreshToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRequest));
    }

    // Test Ref.
    @GetMapping("/user")
    public String findMemberProfile(@AuthenticationPrincipal AuthInfo authInfo) {
        User user = userRepository.findById(authInfo.getId()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_CODE));
        return user.getId();
    }

}