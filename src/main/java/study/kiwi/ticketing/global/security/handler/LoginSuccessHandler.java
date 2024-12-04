package study.kiwi.ticketing.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import study.kiwi.ticketing.global.properties.JwtProperties;
import study.kiwi.ticketing.global.security.domain.MemberDetails;
import study.kiwi.ticketing.global.token.domain.RefreshToken;
import study.kiwi.ticketing.global.token.provider.JwtProvider;
import study.kiwi.ticketing.global.token.repository.RefreshTokenRepository;
import study.kiwi.ticketing.global.token.vo.TokenResponse;
import study.kiwi.ticketing.member.Member;


@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        // 인증 성공 후 처리되는 핸들러이기 때문에 전달받은 authentication 은 인증이 완료된 객체
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();

        // 인증이 성공한 경우 토큰을 생성하여, 응답 헤더에 담아 클라이언트에게 전달
        TokenResponse tokenResponse = jwtProvider.generateToken(member);

        // 인증이 성공했으니 Refresh Token 을 DB에 저장한다
        refreshTokenRepository.save(new RefreshToken(tokenResponse.refreshToken().token(), member.getId()));

        // 헤더로 accessToken 전달
        response.addHeader(JwtProperties.JWT_ACCESS, JwtProperties.JWT_ACCESS_TOKEN_TYPE+ tokenResponse.accessToken().token());
        response.addHeader(JwtProperties.JWT_REFRESH, JwtProperties.JWT_ACCESS_TOKEN_TYPE + tokenResponse.refreshToken().token());
    }
}
