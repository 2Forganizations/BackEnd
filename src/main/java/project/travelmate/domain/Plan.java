package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.base.TimeEntity;
import project.travelmate.domain.enums.Category;
import project.travelmate.request.PlanCreateRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Plan extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    private String title;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer minimumAge;
    private Integer maximumAge;

    @Embedded
    private Address address;

    private String requireRecruitMember;
    private Integer currentRecruitMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_image_id")
    private PlanImage planImage;

    @OneToMany(mappedBy = "plan")
    private List<PlanMember> planMembers = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<WaitMember> waitMembers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Builder
    public Plan(Long id, String title, String content, Category category, LocalDateTime startDate, LocalDateTime endDate, Integer minimumAge, Integer maximumAge, Address address, String requireRecruitMember, Integer currentRecruitMember, User user, PlanImage planImage, List<PlanMember> planMembers, List<WaitMember> waitMembers, ChatRoom chatRoom) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.address = address;
        this.requireRecruitMember = requireRecruitMember;
        this.currentRecruitMember = currentRecruitMember;
        this.user = user;
        this.planImage = planImage;
        this.planMembers = planMembers;
        this.waitMembers = waitMembers;
        this.chatRoom = chatRoom;
    }

    public static Plan of(User user, PlanImage planImage, PlanCreateRequest request) {
        return Plan.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .minimumAge(request.getMinimumAge())
                .maximumAge(request.getMaximumAge())
                .address(getAddress(request))
                .requireRecruitMember(getRequireRecruitMember(request))
                .currentRecruitMember(request.getCurrentRecruitMember())
                .planImage(planImage)
                .user(user)
                .build();
    }

    private static Address getAddress(PlanCreateRequest request) {
        return new Address(request.getNation(), request.getCity(), request.getDetail(), request.getLatitude(), request.getLongitude());
    }

    private static String getRequireRecruitMember(PlanCreateRequest request) {
        return request.getRecruitManNumber() + "/" + request.getRecruitWomanNumber() + "/" + request.getRecruitEtcNumber();
    }

}