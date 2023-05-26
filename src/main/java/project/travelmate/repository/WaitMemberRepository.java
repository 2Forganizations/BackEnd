package project.travelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.travelmate.domain.WaitMember;

@Repository
public interface WaitMemberRepository extends JpaRepository<WaitMember, Long> {
}
