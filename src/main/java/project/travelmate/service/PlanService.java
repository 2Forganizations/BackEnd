package project.travelmate.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.travelmate.advice.ExceptionCodeConst;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.domain.Address;
import project.travelmate.domain.Plan;
import project.travelmate.domain.PlanImage;
import project.travelmate.domain.User;
import project.travelmate.repository.PlanImageRepository;
import project.travelmate.repository.PlanRepository;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.PlanCreateRequest;
import project.travelmate.response.PlanCreateResponse;
import project.travelmate.utils.storage.S3Util;

@Service
public class PlanService {

    private final S3Util s3Util;
    private final UserRepository userRepository;
    private final PlanImageRepository planImageRepository;
    private final PlanRepository planRepository;

    public PlanService(S3Util s3Util, UserRepository userRepository, PlanImageRepository planImageRepository, PlanRepository planRepository) {
        this.s3Util = s3Util;
        this.userRepository = userRepository;
        this.planImageRepository = planImageRepository;
        this.planRepository = planRepository;
    }

    @Transactional
    public PlanCreateResponse create(String userId, MultipartFile image, PlanCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ExceptionCodeConst.USER_NOT_FOUND_CODE));

        String thumbnail = s3Util.saveImage(image);
        PlanImage planImage = PlanImage.builder()
                .filePath(thumbnail)
                .build();
        PlanImage planImageInfo = planImageRepository.save(planImage);

        Plan plan = Plan.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .minimumAge(request.getMinimumAge())
                .maximumAge(request.getMaximumAge())
                .address(new Address(request.getNation(), request.getCity(), request.getDetail(), request.getLatitude(), request.getLongitude()))
                .requireRecruitMember(request.getRecruitManNumber() + "/" + request.getRecruitWomanNumber() + "/" + request.getRecruitEtcNumber())
                .currentRecruitMember(request.getCurrentRecruitMember())
                .planImage(planImage)
                .user(user)
                .build();
        Plan planInfo = planRepository.save(plan);

        return new PlanCreateResponse(planInfo.getId(), planImageInfo.getFilePath());
    }

}