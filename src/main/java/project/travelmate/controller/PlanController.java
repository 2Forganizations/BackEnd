package project.travelmate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.request.AuthInfo;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;
import project.travelmate.service.PlanService;

@RestController
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/plan/create")
    public ResponseEntity<PlanCreateResponse> create(@AuthenticationPrincipal AuthInfo authInfo,
                                                     @RequestParam(value = "thumbnail") MultipartFile image,
                                                     PlanCreateRequest planCreateRequest) {
        PlanCreateResponse planCreateResponse = planService.create(authInfo.getId(), image, planCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(planCreateResponse);
    }

}