package study.kiwi.ticketing.oauth2.utils;

import study.kiwi.ticketing.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.oauth2.userInfo.NaverOAuth2UserInfo;
import study.kiwi.ticketing.oauth2.userInfo.OAuth2UserInfo;

import java.util.Map;

public class OAuth2Utils {

    public static OAuthProviderType getOAuthProviderType(String registrationId) {
        if (registrationId != null) {
            registrationId = registrationId.toUpperCase();
        }
        if ("NAVER".equals(registrationId)) {
            return OAuthProviderType.NAVER;
        }
        return null;
    }

    public static OAuth2UserInfo getOAuth2UserInfo(OAuthProviderType socialType, Map<String, Object> attributes) {
        return new NaverOAuth2UserInfo(attributes);
    }
}
