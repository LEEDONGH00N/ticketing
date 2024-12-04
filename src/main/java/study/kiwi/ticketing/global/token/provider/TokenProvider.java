package study.kiwi.ticketing.global.token.provider;

import study.kiwi.ticketing.global.token.vo.TokenResponse;
import study.kiwi.ticketing.member.Member;

public interface TokenProvider {
    TokenResponse generateToken(Member member);
}
