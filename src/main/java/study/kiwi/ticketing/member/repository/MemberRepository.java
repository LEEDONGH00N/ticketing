package study.kiwi.ticketing.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.kiwi.ticketing.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);

    Optional<Member> findMemberByEmail(String email);
}
