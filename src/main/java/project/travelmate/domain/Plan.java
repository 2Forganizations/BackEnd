package project.travelmate.domain;

import lombok.*;
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

    private Integer minAge;
    private Integer maxAge;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Embedded
    private Address address;

    private String requireRecruitMember;
    private String currentRecruitMember;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="plan_image_id")
    private PlanImage planImage;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanMember> planMembers = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<WaitMember> waitMembers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Builder
    public Plan(String title, String content, Category category, LocalDateTime startDate, LocalDateTime endDate,
                Address address, String requireRecruitMember, String currentRecruitMember, Integer minAge, Integer maxAge) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.requireRecruitMember = requireRecruitMember;
        this.currentRecruitMember = currentRecruitMember;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static Plan createPlan(PlanCreateRequest planCreateRequest, String ownerId) {
        Plan plan = Plan.builder()
                .title(planCreateRequest.getTitle())
                .content(planCreateRequest.getContent())
                .category(planCreateRequest.getCategory())
                .startDate(planCreateRequest.getStartDate())
                .endDate(planCreateRequest.getEndDate())
                .minAge(planCreateRequest.getMinAge())
                .maxAge(planCreateRequest.getMaxAge())
                .address(new Address(planCreateRequest))
                .requireRecruitMember(
                        addForm(planCreateRequest.getRecruitManNumber(),
                                planCreateRequest.getRecruitWomanNumber(),
                                planCreateRequest.getRecruitEtcNumber()
                        )
                )
                .currentRecruitMember(
                        addForm(planCreateRequest.getRecruitManNumber(),
                                planCreateRequest.getRecruitWomanNumber(),
                                planCreateRequest.getRecruitEtcNumber()
                        )
                ).build();
        PlanMember planMember = PlanMember.ofOwner(plan, User.builder().id(ownerId).build());
        plan.planMembers.add(planMember);
        return plan;
    }

    public static String addForm(Integer manNumber, Integer womanNumber, Integer etcNumber) {
        return manNumber + "/" + womanNumber + "/" + etcNumber;
    }

}