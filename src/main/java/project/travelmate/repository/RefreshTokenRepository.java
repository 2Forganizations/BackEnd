package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.travelmate.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}