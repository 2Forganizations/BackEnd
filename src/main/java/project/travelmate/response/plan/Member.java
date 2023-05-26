package project.travelmate.response.plan;

import lombok.Getter;
import project.travelmate.domain.PlanMember;
import project.travelmate.domain.ProfileImage;
import project.travelmate.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class Member {
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

    public static List<Member> makeMembersResponse(List<PlanMember> planMembers) {
        return planMembers.stream().map(Member::new).collect(Collectors.toList());
    }
}
