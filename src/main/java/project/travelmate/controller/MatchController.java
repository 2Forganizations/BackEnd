package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/api/plan/member/create/{waitMemberId}")
    public ResponseEntity<Void> acceptWaitMember(
            @AuthenticationPrincipal AuthInfo authInfo,
            @PathVariable("waitMemberId") Long waitMemberId
    ) {

        matchService.acceptWaitMember(authInfo.getId(), waitMemberId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/api/plan/waitmember/delete/{waitMemberId}")
    public ResponseEntity<Void> deleteWaitMember(
            @AuthenticationPrincipal AuthInfo authInfo,
            @PathVariable("waitMemberId") Long waitMemberId
    ) {

        matchService.deleteWaitMember(authInfo.getId(), waitMemberId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/api/plan/member/delete/{planMemberId}")
    public ResponseEntity<Void> banMember(
            @AuthenticationPrincipal AuthInfo authInfo,
            @PathVariable("planMemberId") Long planMemberId
    ) {

        matchService.banMember(authInfo.getId(), planMemberId);
        return ResponseEntity.ok(null);
    }


}
