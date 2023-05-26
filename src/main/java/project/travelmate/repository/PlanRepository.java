package project.travelmate.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.travelmate.domain.Plan;

import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("select p from Plan p join fetch p.planMembers pl join fetch pl.user where p.id=:planId")
    Optional<Plan> findPlanWithPlanMembersById(@Param("planId") Long planId);

    @Query("select p from Plan p join fetch p.planMembers pl join fetch pl.user where pl.role='OWNER'")
    Slice<Plan> findPlansWithPlanOwner(Pageable pageable);
}
