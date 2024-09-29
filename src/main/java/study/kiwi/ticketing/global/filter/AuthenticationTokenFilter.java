package study.kiwi.ticketing.global.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.global.security.auth.token.JwtProvider;
import study.kiwi.ticketing.member.dto.MemberDetails;
import study.kiwi.ticketing.member.service.MemberDetailsService;

import java.io.IOException;

import static study.kiwi.ticketing.global.codes.ErrorCode.EMPTY_TOKEN_PROVIDED;


@RequiredArgsConstructor
@Slf4j
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;
    @Resource
    private SecurityContextRepository securityContextRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("멤버 인증 시작!!");

        String token = resolveToken(request);

        String aud = jwtProvider.parseAudience(token); // 토큰 Aud에 Member email을 기록하고 있음

        MemberDetails userDetails = memberDetailsService.loadUserByUsername(aud); // memberId를 기반으로 조회

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());


        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        log.info("=======1======");
        filterChain.doFilter(request, response);
        log.info("=======2======");

        log.info("인증 성공");
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null) {
            throw new BaseException(EMPTY_TOKEN_PROVIDED);
        }
        if (authorization.startsWith("Bearer ")) { // Bearer 기반 토큰 인증을 함
            return authorization.substring(7);
        }

        throw new BaseException(EMPTY_TOKEN_PROVIDED);
    }
}
