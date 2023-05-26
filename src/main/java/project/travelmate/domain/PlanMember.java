package project.travelmate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.travelmate.domain.enums.Role;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PlanMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_member_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PlanMember(Role role, Plan plan, User user) {
        this.role = role;
        this.plan = plan;
        this.user = user;
    }

    public static PlanMember ofOwner(Plan plan, User user) {
        return new PlanMember(Role.OWNER, plan, user);
    }
}