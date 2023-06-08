package project.travelmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.travelmate.request.AuthInfo;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.request.PlanSearchRequest;
import project.travelmate.response.plan.CardPlanResponse;
import project.travelmate.response.plan.PlanCreateResponse;
import project.travelmate.response.plan.PlanDetailResponse;
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

    @GetMapping("/api/plans/get")
    public ResponseEntity<Page<CardPlanResponse>> getPlanList(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute PlanSearchRequest planSearchRequest
    ) {
        Page<CardPlanResponse> planListResponse = planService.getPlanList(planSearchRequest, pageable);

        return ResponseEntity.ok(planListResponse);
    }

    @PostMapping("/api/{planId}/delete")
    public ResponseEntity<Void> deletePlan(
            @AuthenticationPrincipal AuthInfo authInfo,
            @PathVariable("planId") Long planId
    ) {
        planService.deletePlan(authInfo.getId(), planId);
        return ResponseEntity.ok(null);
    }
}
