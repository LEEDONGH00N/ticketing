package study.kiwi.ticketing.global.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.kiwi.ticketing.global.auth.member.OAuthAttributes;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.member.dto.MemberAuthContext;
import study.kiwi.ticketing.global.auth.member.MemberDetails;
import study.kiwi.ticketing.member.repository.MemberRepository;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //네이버로부터 유저의 정보를 조회해옴
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthAttributes authAttributes = OAuthAttributes.of(getRegistrationId(userRequest), attributes);
        Member findMember = getMember(authAttributes);
        MemberAuthContext context = MemberAuthContext.ofOAuth(findMember);
        return new MemberDetails(context, attributes);
    }

    private String getUserNameAttributeName(OAuth2UserRequest userRequest) {
        return userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
    }

    private String getRegistrationId(OAuth2UserRequest userRequest) {
        return userRequest
                .getClientRegistration()
                .getRegistrationId()
                .toUpperCase();
    }

    private Member getMember(OAuthAttributes attributes) {
        return memberRepository
                .findByOauthIdAndProviderType(attributes.id(), attributes.type())
                .orElseGet(() -> createMember(attributes));
    }

    private Member createMember(OAuthAttributes attributes) {
        Member member = Member.fromOAuth(attributes);
        memberRepository.save(member);
        return member;
    }
}

