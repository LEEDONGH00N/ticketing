package study.kiwi.ticketing.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.kiwi.ticketing.domain.member.Member;
import study.kiwi.ticketing.global.oauth2.domain.OAuthProviderType;

import java.util.Optional;

public interface  MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(String email);

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findByOauthIdAndProviderType(String oauthId, OAuthProviderType providerType);
}
