package study.kiwi.ticketing.global.security.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import study.kiwi.ticketing.global.codes.ErrorCode;
import study.kiwi.ticketing.global.common.ApiResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuthFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        ApiResponse<Object> basicResponse = ApiResponse
                .onFailure(ErrorCode.INVALID_EMAIL_OR_PASSWORD.getCode(), ErrorCode.INVALID_EMAIL_OR_PASSWORD.getMessage(), null);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(basicResponse));
    }
}