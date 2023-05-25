package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.travelmate.domain.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
