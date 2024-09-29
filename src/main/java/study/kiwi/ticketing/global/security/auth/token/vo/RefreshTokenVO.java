package study.kiwi.ticketing.global.security.auth.token.vo;

public record RefreshTokenVO(
        String token
) {
    public static RefreshTokenVO of(String token) {
        return new RefreshTokenVO(token);
    }
}

