package study.kiwi.ticketing.global.token.provider;

import study.kiwi.ticketing.global.token.vo.TokenResponse;
import study.kiwi.ticketing.domain.member.dto.AuthenticatedMember;

public interface TokenProvider {
    TokenResponse generateToken(AuthenticatedMember authenticatedMember);
}
