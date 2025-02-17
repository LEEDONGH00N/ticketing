package study.kiwi.ticketing.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import study.kiwi.ticketing.domain.member.Member;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class AuthenticatedMember {
    private Long memberId;
    private String email;
    private String name;
    private Member.Role role;
    private String encodedPassword;
    private Collection<? extends GrantedAuthority> authorities;

    public static AuthenticatedMember from(Member member){
        return new AuthenticatedMember(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getEncodedPassword(),
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()))
        );
    }
}
