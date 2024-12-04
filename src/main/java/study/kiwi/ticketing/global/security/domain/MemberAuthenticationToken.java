package study.kiwi.ticketing.global.security.domain;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import study.kiwi.ticketing.member.dto.AuthenticatedMember;
import study.kiwi.ticketing.member.dto.MemberRequest;

public class MemberAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;
    private String credentials;

    private MemberAuthenticationToken(String email, String password) {
        super(null);
        this.principal = email;
        this.credentials = password;
        this.setAuthenticated(false);
    }

    private MemberAuthenticationToken(Object principal, String credentials) {
        super(null);
        this.principal = principal;
        this.credentials = null;
        this.setAuthenticated(true);
    }

    // 인증 처리 전 객체
    public static MemberAuthenticationToken unauthenticated(MemberRequest.MemberLoginReqDto request){
        return new MemberAuthenticationToken(request.email(), request.password());
    }

    // 인증 처리 후 객체
    public static MemberAuthenticationToken authenticated(AuthenticatedMember principal){
        return new MemberAuthenticationToken(principal, null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
