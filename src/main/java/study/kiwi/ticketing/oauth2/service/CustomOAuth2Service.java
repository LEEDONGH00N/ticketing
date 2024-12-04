package study.kiwi.ticketing.oauth2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.kiwi.ticketing.oauth2.domain.OAuth2Attributes;
import study.kiwi.ticketing.oauth2.domain.OAuth2UserImpl;
import study.kiwi.ticketing.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.oauth2.userInfo.OAuth2UserInfo;
import study.kiwi.ticketing.member.Member;
import study.kiwi.ticketing.member.repository.MemberRepository;
import study.kiwi.ticketing.oauth2.utils.OAuth2Utils;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 네이버로부터 유저의 정보를 조회해옴
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info(attributes.toString());
        OAuthProviderType type = OAuth2Utils.getOAuthProviderType(getRegistrationId(userRequest));
        OAuth2Attributes oauthAttributes = OAuth2Attributes.of(type,
                getUserNameAttributeName(userRequest),
                attributes);
        OAuth2UserInfo oAuth2UserInfo = oauthAttributes.getOauth2UserInfo();

        Member member = getMember(oAuth2UserInfo, type);
        return new OAuth2UserImpl(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                attributes,
                oauthAttributes.getNameAttributeKey(),
                member
        );
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

    private Member getMember(OAuth2UserInfo userInfo, OAuthProviderType type) {
        return memberRepository
                .findByOauthIdAndProviderType(userInfo.getSocialId(), type)
                .orElse(Member.createMemberGuest(userInfo, type));
    }
}

