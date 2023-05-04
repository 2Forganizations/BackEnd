package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.base.TimeEntity;
import project.travelmate.domain.enums.Category;

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
    private PlanImage PlanImage;

    @OneToMany(mappedBy = "plan")
    private List<PlanMember> planMembers = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<WaitMember> waitMembers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Builder
    public Plan(Long id, String title, String content, Category category, LocalDateTime startDate, LocalDateTime endDate, Integer minimumAge, Integer maximumAge, Address address, String requireRecruitMember, Integer currentRecruitMember, User user, project.travelmate.domain.PlanImage planImage, ChatRoom chatRoom) {
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
        PlanImage = planImage;
        this.chatRoom = chatRoom;
    }

}