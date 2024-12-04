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
import study.kiwi.ticketing.global.token.JwtProvider;
import study.kiwi.ticketing.oauth2.domain.MemberDetails;
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
        String token = resolveToken(request);
        String aud = jwtProvider.parseAudience(token);
        MemberDetails userDetails = memberDetailsService.loadUserByUsername(aud);
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null) {
            throw new BaseException(EMPTY_TOKEN_PROVIDED);
        }
        if (authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        throw new BaseException(EMPTY_TOKEN_PROVIDED);
    }
}
