package study.kiwi.ticketing.global.oauth2.domain;

import lombok.Getter;

@Getter
public enum OAuthProviderType {
    NAVER("NAVER");

    private final String provider;

    OAuthProviderType(String provider) {
        this.provider = provider;
    }
}