package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.travelmate.request.AuthInfo;
import project.travelmate.service.MatchService;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/api/plan/{planId}/waitmember/create")
    public ResponseEntity<Void> createWaitMember(
            @PathVariable("planId") Long planId,
            @AuthenticationPrincipal AuthInfo authInfo
    ) {
        matchService.createWaitMember(authInfo.getId(), planId);

        return ResponseEntity.ok(null);
    }

}
