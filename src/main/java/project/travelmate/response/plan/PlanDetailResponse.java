package project.travelmate.response.plan;

import lombok.Getter;
import project.travelmate.domain.*;
import project.travelmate.domain.enums.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static project.travelmate.response.plan.Member.makeMembersResponse;

@Getter
public class PlanDetailResponse {

    private Long planId;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer minAge;
    private Integer maxAge;

    private Address address;
    private RecruitNumber recruitNumber;
    private List<Member> members;

    public PlanDetailResponse(Plan plan) {
        this.planId = plan.getId();
        this.title = plan.getTitle();
        this.content = plan.getContent();
        this.category = plan.getCategory();
        this.startDate = plan.getStartDate();
        this.endDate = plan.getEndDate();
        this.minAge = plan.getMinAge();
        this.maxAge = plan.getMaxAge();
        this.address = plan.getAddress();
        this.recruitNumber = new RecruitNumber(plan);
        this.members = makeMembersResponse(plan.getPlanMembers());
    }



}
