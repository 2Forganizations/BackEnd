package project.travelmate.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.travelmate.request.TokenRequest;
import project.travelmate.response.SignInResponse;
import project.travelmate.service.AuthService;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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

}