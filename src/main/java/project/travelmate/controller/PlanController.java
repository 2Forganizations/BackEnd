package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.travelmate.request.AuthInfo;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;
import project.travelmate.response.PlanDetailResponse;
import project.travelmate.service.PlanService;

@RestController
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping("/api/plan/create")
    public ResponseEntity<PlanCreateResponse> createPlan(
            @AuthenticationPrincipal AuthInfo authInfo,
            @RequestBody PlanCreateRequest planCreateRequest
    ) {
        PlanCreateResponse plan = planService.createPlan(authInfo.getId(), planCreateRequest);

        return ResponseEntity.ok(plan);
    }

    @GetMapping("/api/{planId}/get")
    public ResponseEntity<PlanDetailResponse> getPlan(
            @PathVariable("planId") Long planId
    ) {
        PlanDetailResponse planDetailResponse = planService.getPlanDetail(planId);

        return ResponseEntity.ok(planDetailResponse);
    }

}
