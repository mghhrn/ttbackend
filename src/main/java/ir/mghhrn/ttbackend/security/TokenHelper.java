package ir.mghhrn.ttbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ir.mghhrn.ttbackend.dto.UserTokenDto;
import ir.mghhrn.ttbackend.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Component
public class TokenHelper {

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    private final String secret;
    private final String applicationName;
    private final long expiresAccessInMinute;
    private final long expiresRefreshInMonth;
    private final String authHeader;

    public TokenHelper(@Value("${jwt.secret}") String jwtSecret,
                       @Value("${spring.application.name}") String applicationName,
                       @Value("${jwt.access.expires_in.minute}") long expiresAccessInMinute,
                       @Value("${jwt.refresh.expires_in.month}") long expiresRefreshInMonth,
                       @Value("${jwt.header}") String authHeader) {
        this.secret = jwtSecret;
        this.applicationName = applicationName;
        this.expiresAccessInMinute = expiresAccessInMinute;
        this.expiresRefreshInMonth = expiresRefreshInMonth;
        this.authHeader = authHeader;
    }

    public UserTokenDto generateUserToken(User user) {
        return new UserTokenDto(
                generateAccessToken(user, generateAccessTokenExpirationDate()),
                generateRefreshToken(user, generateRefreshTokenExpirationDate()),
                expiresAccessInMinute,
                user.getId());
    }

    public String generateAccessToken(User user, Date expireDate) {
        return Jwts.builder()
                .setIssuer(applicationName)
                .setSubject(String.valueOf(user.getId()))
                .claim(JwtTokenType.CLAIM_KEY, JwtTokenType.ACCESS.name())
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expireDate)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    public String generateRefreshToken(User user, Date expireDate) {
        return Jwts.builder()
                .setIssuer(applicationName)
                .setSubject(String.valueOf(user.getId()))
                .claim(JwtTokenType.CLAIM_KEY, JwtTokenType.REFRESH.name())
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expireDate)
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    public String generateAccessToken(User user) {
        return generateAccessToken(user, generateAccessTokenExpirationDate());
    }

    public long getAccessTokenExpireTimeInMinute() {
        return expiresAccessInMinute;
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaimsFromToken(token, Claims::getSubject));
    }

    public String getToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(authHeader);
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            return authenticationHeader.substring(7);
        }
        return null;
    }

    public JwtTokenType getTokenTypeFromToken(String token) {
        return getClaimsFromToken(token, claims -> JwtTokenType.valueOf((String) claims.get(JwtTokenType.CLAIM_KEY)));
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date generateAccessTokenExpirationDate() {
        return Date.from(LocalDateTime.now().plusMinutes(expiresAccessInMinute)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date generateRefreshTokenExpirationDate() {
        return Date.from(LocalDateTime.now().plusMonths(expiresRefreshInMonth)
                .atZone(ZoneId.systemDefault()).toInstant());
    }
}
