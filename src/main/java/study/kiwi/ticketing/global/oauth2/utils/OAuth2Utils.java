package study.kiwi.ticketing.global.oauth2.utils;

import study.kiwi.ticketing.global.oauth2.domain.OAuthProviderType;
import study.kiwi.ticketing.global.oauth2.userInfo.NaverOAuth2UserInfo;
import study.kiwi.ticketing.global.oauth2.userInfo.OAuth2UserInfo;

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

    // 지금은 네이버밖에 없기 때문에 socialType에 대한 분기를 진행하지 않았음
    public static OAuth2UserInfo getOAuth2UserInfo(OAuthProviderType socialType,
                                                   Map<String, Object> attributes) {
//        if(socialType.equals("NAVER"))
//            return new NaverOAuth2UserInfo(attributes);
        return new NaverOAuth2UserInfo(attributes);
    }
}
