package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.travelmate.domain.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}