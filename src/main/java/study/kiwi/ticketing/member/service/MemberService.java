package study.kiwi.ticketing.member.service;

import jakarta.servlet.http.HttpServletResponse;
import study.kiwi.ticketing.global.security.auth.token.vo.TokenResponse;
import study.kiwi.ticketing.member.dto.MemberRequest;

public interface MemberService {

    String memberSignup(MemberRequest.MemberSignupReqDto request);
    TokenResponse memberLogin(MemberRequest.MemberLoginReqDto request, HttpServletResponse response);
}
