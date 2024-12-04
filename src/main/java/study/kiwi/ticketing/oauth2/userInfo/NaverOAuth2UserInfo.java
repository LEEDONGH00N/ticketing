package study.kiwi.ticketing.oauth2.userInfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public static Map<String, Object> responseMap;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        responseMap = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getSocialId() {
        return String.valueOf(responseMap.get("id"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(responseMap.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(responseMap.get("name"));
    }

    @Override
    public String getPhoneNum() {
        return String.valueOf(responseMap.get("mobile"));
    }
}
