package study.kiwi.ticketing.member;

import lombok.Getter;

@Getter
public enum OAuthProviderType {
    NAVER("naver");

    private final String provider;

    OAuthProviderType(String provider) {
        this.provider = provider;
    }
}