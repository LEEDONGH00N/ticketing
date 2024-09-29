package study.kiwi.ticketing.global.security.auth.token.vo;


public record TokenResponse(
        AccessTokenVO accessToken,
        RefreshTokenVO refreshToken
) {
    public static TokenResponse of(AccessTokenVO accessToken, RefreshTokenVO refreshToken) {
        return new TokenResponse(accessToken, refreshToken);
    }
}
