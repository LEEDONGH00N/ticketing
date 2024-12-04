package study.kiwi.ticketing.global.token.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import study.kiwi.ticketing.global.token.vo.AccessTokenVO;
import study.kiwi.ticketing.global.token.vo.RefreshTokenVO;
import study.kiwi.ticketing.global.token.vo.TokenResponse;
import study.kiwi.ticketing.global.common.BaseException;
import study.kiwi.ticketing.member.Member;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static study.kiwi.ticketing.global.codes.ErrorCode.*;
import static study.kiwi.ticketing.global.properties.JwtProperties.ACCESS_TOKEN_EXPIRE_TIME;
import static study.kiwi.ticketing.global.properties.JwtProperties.REFRESH_TOKEN_EXPIRE_TIME;


@Getter
@Component
@Slf4j
public class JwtProvider implements TokenProvider {

    private final SecretKey SECRET_KEY;
    private final String ISS = "leedonghoon";

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey
    ) {
        byte[] keyBytes = Base64.getDecoder()
                .decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
    }


    public TokenResponse generateToken(Member member){
        AccessTokenVO accessToken = generateAccessToken(member);
        RefreshTokenVO refreshToken = generateRefreshToken(member);

       return new TokenResponse(accessToken, refreshToken);
    }

    public AccessTokenVO generateAccessToken(Member member) {
        if (member.getEmail() == null || member.getEmail().isBlank()) {
            return AccessTokenVO.of("");
        }
        return this.generateAccessToken(member.getEmail());
    }

    private AccessTokenVO generateAccessToken(String email) {
        String token = Jwts.builder()
                .claim("type", "access")
                .issuer(ISS)
                .audience().add(email).and()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();

        log.info("[generateAccessToken] {}", token);
        return AccessTokenVO.of(token);
    }

    public RefreshTokenVO generateRefreshToken(Member member) {
        if (member.getEmail() == null || member.getEmail().isBlank()) {
            return RefreshTokenVO.of("");
        }
        return this.generateRefreshToken(member.getEmail());
    }

    private RefreshTokenVO generateRefreshToken(String email) {
        String token = Jwts.builder()
                .claim("type", "refresh")
                .issuer(ISS)
                .audience().add(email).and()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SECRET_KEY)
                .compact();

        log.info("[generateRefreshToken] {}", token);
        return RefreshTokenVO.of(token);
    }

    public String parseAudience(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            if (claims.getPayload()
                    .getExpiration()
                    .before(new Date())) {
                throw new BaseException(EXPIRED_ACCESS_TOKEN);
            }

            String aud = claims.getPayload()
                    .getAudience()
                    .iterator()
                    .next();

            return aud;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("[parseAudience] {} :{}", INVALID_TOKEN, token);
            throw new BaseException(INVALID_TOKEN);
        } catch (BaseException e) {
            log.warn("[parseAudience] {} :{}", EXPIRED_ACCESS_TOKEN, token);
            throw new BaseException(EXPIRED_ACCESS_TOKEN);
        }
    }
}
