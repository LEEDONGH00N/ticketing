package study.kiwi.ticketing.oauth2.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import study.kiwi.ticketing.global.token.domain.RefreshToken;
import study.kiwi.ticketing.global.token.provider.JwtProvider;
import study.kiwi.ticketing.global.token.repository.RefreshTokenRepository;
import study.kiwi.ticketing.global.token.vo.AccessTokenVO;
import study.kiwi.ticketing.global.token.vo.TokenResponse;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.oauth2.domain.OAuth2UserImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static study.kiwi.ticketing.global.properties.JwtProperties.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Member member = ((OAuth2UserImpl) authentication.getPrincipal()).getMember();
        TokenResponse tokenResponse = jwtProvider.generateToken(member);
        if(member.getRole().equals(Member.Role.GUEST)){
            redirectToSignupWithUserInfo(request, response, member, tokenResponse.accessToken());
            return;
        }
        refreshTokenRepository.save(new RefreshToken(tokenResponse.refreshToken().token(), member.getId()));
        redirectAfterLoginSuccess(request, response, tokenResponse);
    }

    private void redirectAfterLoginSuccess(HttpServletRequest request, HttpServletResponse response, TokenResponse tokenResponse) throws IOException {
        response.addHeader(JWT_ACCESS, JWT_ACCESS_TOKEN_TYPE + tokenResponse.accessToken());
        response.addHeader(JWT_REFRESH, JWT_ACCESS_TOKEN_TYPE + tokenResponse.refreshToken());
        // 최초 로그인이 아닌 경우 로그인 성공 페이지로 이동
        String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }
    // 최초 로그인인 경우 추가 정보 입력을 위한 회원가입 페이지로 리다이렉트
    private void redirectToSignupWithUserInfo(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Member member,
                                              AccessTokenVO accessToken) throws IOException {
        log.info("최초 로그인인 경우 추가 정보 입력을 위한 회원가입 페이지로 리다이렉트 ");
        response.addHeader(JWT_REFRESH_TOKEN_COOKIE_NAME, JWT_ACCESS_TOKEN_TYPE + accessToken);
        String redirectURL = createRedirectUri(member);
        getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }

    private String createRedirectUri(Member member) {
        return UriComponentsBuilder.fromUriString("http://localhost:8080/oauth2/signup")
                .queryParam("email", member.getEmail())
                .queryParam("providerType", member.getProviderType())
                .queryParam("oauthId", member.getOauthId())
                .queryParam("name", member.getName())
                .queryParam("phoneNum", member.getPhoneNum())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }
}
