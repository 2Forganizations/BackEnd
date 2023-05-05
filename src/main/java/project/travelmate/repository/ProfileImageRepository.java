package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.travelmate.domain.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}