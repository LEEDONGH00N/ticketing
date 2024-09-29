package study.kiwi.ticketing.global.security.auth.token;


import study.kiwi.ticketing.global.security.auth.token.vo.AccessTokenVO;
import study.kiwi.ticketing.global.security.auth.token.vo.RefreshTokenVO;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.member.dto.MemberDTO;

public interface TokenProvider {
    AccessTokenVO generateAccessToken(Member member);
    AccessTokenVO generateAccessToken(MemberDTO memberDTO);

    RefreshTokenVO generateRefreshToken(Member member);
    RefreshTokenVO generateRefreshToken(MemberDTO memberDTO);
}
