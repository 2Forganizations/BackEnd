package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.travelmate.domain.PlanImage;

public interface PlanImageRepository extends JpaRepository<PlanImage, Long> {
}