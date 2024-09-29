package study.kiwi.ticketing.global.security.auth.token.vo;

public record AccessTokenVO(
        String token
) {
    public static AccessTokenVO of(String token) {
        return new AccessTokenVO(token);
    }
}