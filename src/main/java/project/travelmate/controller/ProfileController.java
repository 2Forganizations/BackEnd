package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.travelmate.request.AuthInfo;
import project.travelmate.response.ProfileResponse;
import project.travelmate.service.ProfileService;

@RestController()
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/api/member/get")
    public ResponseEntity<ProfileResponse> getProfile(@AuthenticationPrincipal AuthInfo authInfo) {
        ProfileResponse profileResponse = profileService.getProfile(authInfo.getId());

        return ResponseEntity.ok(profileResponse);
    }


}
