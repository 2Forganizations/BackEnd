package project.travelmate.domain;

import lombok.AccessLevel;
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

    @Embedded
    private Address address;

    private Integer requireRecruitMember;
    private Integer currentRecruitMember;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="planimage_id")
    private PlanImage PlanImage;

    @OneToMany(mappedBy = "plan")
    private List<PlanMember> planMembers = new ArrayList<>();

    @OneToMany(mappedBy = "plan")
    private List<WaitMember> waitMembers = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

}