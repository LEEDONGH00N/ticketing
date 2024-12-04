package study.kiwi.ticketing.oauth2.domain;

import lombok.Getter;

@Getter
public enum OAuthProviderType {
    NAVER("naver");

    private final String provider;

    OAuthProviderType(String provider) {
        this.provider = provider;
    }
}