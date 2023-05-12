package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.request.AuthInfo;
import project.travelmate.request.MemberProfileUpdateRequest;
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

    @PostMapping("/api/member/profile/edit")
    public ResponseEntity<Void> editProfile(
            @RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest,
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        profileService.editProfile(authInfo.getId(), memberProfileUpdateRequest);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/api/member/profileimage/edit")
    public ResponseEntity<Void> editProfileImage(
        @AuthenticationPrincipal AuthInfo authInfo,
        @RequestPart(value = "profileImage") MultipartFile imageFile
    ) {
        profileService.editProfileImage(authInfo.getId(), imageFile);

        return ResponseEntity.ok(null);
    }

}
