package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.travelmate.domain.User;
import project.travelmate.domain.enums.AuthProvider;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByIdAndAuthProvider(String id, AuthProvider authProvider);

    @Query("select u from User u where u.id = :userId")
    Optional<User> findUserWithProfileImageById(@Param("userId") String id);
}