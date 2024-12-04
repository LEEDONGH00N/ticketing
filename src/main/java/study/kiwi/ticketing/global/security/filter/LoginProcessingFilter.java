package study.kiwi.ticketing.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import study.kiwi.ticketing.global.security.domain.MemberAuthenticationToken;
import study.kiwi.ticketing.member.dto.MemberRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public LoginProcessingFilter() {
        super(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        MemberRequest.MemberLoginReqDto loginRequest = parseToDto(request);
        MemberAuthenticationToken token = MemberAuthenticationToken.unauthenticated(loginRequest);
        return super.getAuthenticationManager().authenticate(token);
    }

    private MemberRequest.MemberLoginReqDto parseToDto(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, MemberRequest.MemberLoginReqDto.class);
    }
}
