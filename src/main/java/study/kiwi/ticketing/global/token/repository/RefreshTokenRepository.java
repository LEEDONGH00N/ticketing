package study.kiwi.ticketing.global.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.kiwi.ticketing.global.token.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
