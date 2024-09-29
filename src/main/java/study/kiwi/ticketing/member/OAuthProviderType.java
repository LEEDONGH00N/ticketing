package study.kiwi.ticketing.member;

import lombok.Getter;

@Getter
public enum OAuthProviderType {
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver"),
    LOCAL("local");

    private final String provider;

    OAuthProviderType(String provider) {
        this.provider = provider;
    }
}