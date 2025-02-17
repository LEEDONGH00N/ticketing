package study.kiwi.ticketing.global.security.domain;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import study.kiwi.ticketing.domain.member.dto.AuthenticatedMember;
import study.kiwi.ticketing.domain.member.dto.MemberRequest;

import java.util.Collection;

public class MemberAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentials;

    private MemberAuthenticationToken(String email, String password) {
        super(null);
        this.principal = email;
        this.credentials = password;
        this.setAuthenticated(false);
    }

    private MemberAuthenticationToken(Object principal,
                                      Object credentials,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    // 인증 처리 전 객체
    public static MemberAuthenticationToken unauthenticated(MemberRequest.MemberLoginReqDto request){
        return new MemberAuthenticationToken(request.email(), request.password());
    }

    // 인증 처리 후 객체
    public static MemberAuthenticationToken authenticated(AuthenticatedMember authentication){
        return new MemberAuthenticationToken(authentication,
                authentication.getEncodedPassword(),
                authentication.getAuthorities());
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
