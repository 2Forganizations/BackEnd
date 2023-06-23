package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.travelmate.domain.PlanMember;

@Repository
public interface PlanMemberRepository extends JpaRepository<PlanMember, Long> {
}
