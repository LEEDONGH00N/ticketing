package study.kiwi.ticketing.member.service;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.properties.JwtProperties;
import study.kiwi.ticketing.global.security.auth.token.JwtProvider;
import study.kiwi.ticketing.global.security.auth.token.vo.RefreshTokenVO;
import study.kiwi.ticketing.global.security.auth.token.vo.TokenResponse;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.member.repository.MemberRepository;

import static study.kiwi.ticketing.global.codes.ErrorCode.MEMBER_NOT_FOUND;
import static study.kiwi.ticketing.global.codes.ErrorCode.PASSWORD_ERROR;
import static study.kiwi.ticketing.member.dto.MemberRequest.*;
import static study.kiwi.ticketing.global.properties.JwtProperties.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public String memberSignup(MemberSignupReqDto request){
        Member member = Member.from(request, passwordEncoder.encode(request.password()));
        memberRepository.save(member);
        return "회원가입 완료";
    }

    @Transactional
    public TokenResponse memberLogin(MemberLoginReqDto request,
                                    HttpServletResponse response) {

        Member member = memberRepository.findMemberByEmail(request.email()).orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));
        if(!passwordEncoder.matches(request.password(), member.getEncodedPassword())){
            log.info("비밀번호 틀림");
            throw new BaseException(PASSWORD_ERROR);
        }
        TokenResponse tokenResponse = jwtProvider.generateToken(member);
        response.addHeader(JWT_ACCESS_TOKEN_HEADER_NAME, JWT_ACCESS_TOKEN_TYPE + tokenResponse.accessToken().token());

        sendRefreshTokenToCookie(response, tokenResponse.refreshToken());
        return tokenResponse;
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
