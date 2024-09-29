//package study.kiwi.ticketing.global.security.auth.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import study.kiwi.ticketing.global.security.auth.token.JwtProvider;
//import study.kiwi.ticketing.global.security.auth.token.vo.AccessTokenVO;
//import study.kiwi.ticketing.global.security.auth.token.vo.RefreshTokenVO;
//import study.kiwi.ticketing.global.security.auth.token.vo.TokenResponse;
//import study.kiwi.ticketing.member.Member;
//import study.kiwi.ticketing.member.OAuthProviderType;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Component
//public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
//
//    private final MemberServiceImpl memberService;
//    private final JwtProvider jwtProvider;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        String registrationId = oAuth2User.getAttributes().get("registrationId").toString();
//
//        String oauthId = null;
//        OAuthProviderType oAuthProviderType = null;
//        if (registrationId.equals(OAuthProviderType.GOOGLE.getProvider())) {
//            oauthId = attributes.get("id").toString();
//            oAuthProviderType = OAuthProviderType.GOOGLE;
//        }
//
//        Member member = memberService.findMemberByOAuthId(oauthId, oAuthProviderType);
//
//        AccessTokenVO accessToken = jwtProvider.generateAccessToken(member);
//        RefreshTokenVO refreshToken = jwtProvider.generateRefreshToken(member);
//        TokenResponse tokenResponse = TokenResponse.of(accessToken, refreshToken);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
//    }
//}
