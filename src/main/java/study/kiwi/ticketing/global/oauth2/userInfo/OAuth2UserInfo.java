package study.kiwi.ticketing.global.oauth2.userInfo;

import lombok.AllArgsConstructor;
import study.kiwi.ticketing.global.oauth2.domain.OAuthProviderType;

import java.util.Map;

@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getOAuthId();
    public abstract String getEmail();
    public abstract String getName();
    public abstract String getPhoneNum();
    public abstract OAuthProviderType getProviderType();
}
