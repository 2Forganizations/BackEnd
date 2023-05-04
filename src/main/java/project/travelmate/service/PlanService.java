package project.travelmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.Plan;
import project.travelmate.domain.PlanImage;
import project.travelmate.domain.User;
import project.travelmate.repository.PlanRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;
import project.travelmate.utils.storage.S3Util;

@Service
public class PlanService {

    private final S3Util s3Util;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public PlanService(S3Util s3Util, UserRepository userRepository, PlanRepository planRepository) {
        this.s3Util = s3Util;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Transactional
    public PlanCreateResponse create(String userId, MultipartFile image, PlanCreateRequest request) {
        User user = getUser(userId);
        PlanImage planImageInfo = createPlanImage(image);

        Plan plan = Plan.of(user, planImageInfo, request);
        planRepository.save(plan);

        return new PlanCreateResponse(plan, planImageInfo);
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private PlanImage createPlanImage(MultipartFile image) {
        String thumbnail = s3Util.saveImage(image);
        return PlanImage.builder()
                .filePath(thumbnail)
                .build();
    }

}