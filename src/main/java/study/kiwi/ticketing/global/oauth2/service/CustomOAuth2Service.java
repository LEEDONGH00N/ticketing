package study.kiwi.ticketing.global.oauth2.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import study.kiwi.ticketing.global.oauth2.utils.OAuth2Utils;
import study.kiwi.ticketing.global.security.domain.PrincipalDetails;
import study.kiwi.ticketing.domain.member.dto.AuthenticatedMember;
import study.kiwi.ticketing.global.oauth2.domain.OAuth2Attributes;
import study.kiwi.ticketing.global.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.global.oauth2.userInfo.OAuth2UserInfo;
import study.kiwi.ticketing.domain.member.Member;
import study.kiwi.ticketing.domain.member.repository.MemberRepository;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2Service extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User =  super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthProviderType type = OAuth2Utils.getOAuthProviderType(getRegistrationId(userRequest));
        OAuth2Attributes oauthAttributes = OAuth2Attributes.of(
                type,
                getUserNameAttributeName(userRequest),
                attributes
        );
        OAuth2UserInfo oAuth2UserInfo = oauthAttributes.getOauth2UserInfo();
        Member member = findMember(oAuth2UserInfo);
        AuthenticatedMember authenticatedMember = AuthenticatedMember.from(member);
        return PrincipalDetails.from(
                authenticatedMember,
                attributes
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

    private Member findMember(OAuth2UserInfo userInfo) {
        return memberRepository
                .findByOauthIdAndProviderType(userInfo.getOAuthId(), userInfo.getProviderType())
                .orElse(Member.createMemberGuest(userInfo));
    }
}

