package study.kiwi.ticketing.global.token.vo;

public record RefreshTokenVO(
        String token
) {
    public static RefreshTokenVO of(String token) {
        return new RefreshTokenVO(token);
    }
}

