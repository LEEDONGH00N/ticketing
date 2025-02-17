package study.kiwi.ticketing.domain.member.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.token.vo.RefreshTokenVO;
import study.kiwi.ticketing.domain.member.Member;
import study.kiwi.ticketing.global.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.domain.member.repository.MemberRepository;

import static study.kiwi.ticketing.global.properties.JwtProperties.*;
import static study.kiwi.ticketing.domain.member.dto.MemberRequest.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String signupAfterNaverLogin(MemberNaverSignupReqDto request) {
        if (memberRepository.findByOauthIdAndProviderType(request.oauthId(), OAuthProviderType.valueOf(request.providerType())).isPresent()){
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        memberRepository.save(Member.createMember(request));
        return "회원가입 완료";
    }

    @Transactional
    public String signup(MemberLocalSignupReqDto request){
        memberRepository.save(Member.createMember(request));
        return "회원가입 완료";
    }

    private void sendRefreshTokenToCookie(HttpServletResponse response,
                                          RefreshTokenVO refreshToken) {
        Cookie refreshTokenCookie = new Cookie(JWT_REFRESH_TOKEN_COOKIE_NAME, refreshToken.token());
        refreshTokenCookie.setMaxAge((int) REFRESH_TOKEN_EXPIRE_TIME);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);
    }
}
