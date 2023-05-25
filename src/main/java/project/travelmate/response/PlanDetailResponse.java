package project.travelmate.response;

import lombok.Getter;
import project.travelmate.domain.*;
import project.travelmate.domain.enums.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class PlanDetailResponse {

    private Long PlanId;
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
        this.PlanId = plan.getId();
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

    @Getter
    public static class RecruitNumber {
        private String recruitManNumber;
        private String recruitWomanNumber;
        private String recruitEtcNumber;

        public RecruitNumber(Plan plan) {
            String[] requireRecruitMember = plan.getRequireRecruitMember().split("/");
            String[] currentRecruitMember = plan.getCurrentRecruitMember().split("/");

            this.recruitManNumber =
                    stringMinus(requireRecruitMember[0], currentRecruitMember[0]) + "/" + requireRecruitMember[0];
            this.recruitWomanNumber =
                    stringMinus(requireRecruitMember[1], currentRecruitMember[1]) + "/" + requireRecruitMember[1];
            this.recruitEtcNumber =
                    stringMinus(requireRecruitMember[2], currentRecruitMember[2]) + "/" + requireRecruitMember[2];

        }
        private String stringMinus(String a, String b) {
            int c = Integer.parseInt(a) - Integer.parseInt(b);
            return Integer.toString(c);
        }
    }

    @Getter
    public static class Member {
        private String memberId;
        private String username;
        private String profileImage;

        public Member(PlanMember planMember) {
            User user = planMember.getUser();
            this.memberId = user.getId();
            this.username = user.getName();
            this.profileImage = Optional.ofNullable(user.getProfileImage())
                    .map(ProfileImage::getFilePath).orElse(null);
        }
    }

    private List<Member> makeMembersResponse(List<PlanMember> planMembers) {
        return planMembers.stream().map(Member::new).collect(Collectors.toList());
    }

}
