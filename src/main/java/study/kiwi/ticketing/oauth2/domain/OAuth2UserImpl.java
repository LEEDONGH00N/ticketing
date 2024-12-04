package study.kiwi.ticketing.oauth2.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import study.kiwi.ticketing.member.Member;

import java.util.Collection;
import java.util.Map;

@Getter
public class OAuth2UserImpl extends DefaultOAuth2User {

    private final Member member;  // 토큰 만들기 위해 Member 객체를 담아서 전달

    public OAuth2UserImpl(Collection<? extends GrantedAuthority> authorities,
                          Map<String, Object> attributes,
                          String nameAttributeKey,
                          Member member) {
        super(authorities, attributes, nameAttributeKey);
        this.member = member;
    }
}
