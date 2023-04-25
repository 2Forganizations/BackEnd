package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.AuthProvider;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByIdAndAuthProvider(String id, AuthProvider authProvider);

}