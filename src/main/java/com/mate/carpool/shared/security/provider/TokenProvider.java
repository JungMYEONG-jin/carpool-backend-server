package com.mate.carpool.shared.security.provider;

import com.mate.carpool.domain.member.dto.TokenDTO;
import com.mate.carpool.shared.exception.CustomHttpException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 ; // 테스트 환경 1시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 30; // 1달

    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] decode = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decode);
    }


    public TokenDTO generateAccessToken(Authentication authentication, String refreshToken) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        // expiration time
        Date exp = new Date(now + TOKEN_EXPIRE_TIME);
        log.info("exp {}", exp.getTime());
        Date refreshExp = parseClaims(refreshToken).getExpiration();
        log.info("refreshExp {}", refreshExp.getTime());
        // create access token
        String accessToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(exp.getTime())
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshExp.getTime())
                .build();
    }

    /**
     * 리프레쉬 토큰과, 액세스 토큰을 함께 발행한다.
     * @param authentication
     * @return
     */
    public TokenDTO generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        // expiration time
        Date exp = new Date(now + TOKEN_EXPIRE_TIME);
        log.info("exp {}", exp.getTime());
        Date refreshExp = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        log.info("refreshExp {}", refreshExp.getTime());
        // create access token
        String accessToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        String refreshToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(refreshExp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(exp.getTime())
                .refreshTokenExpiresIn(refreshExp.getTime())
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // decode
        Claims claims = parseClaims(accessToken);

        // no authority
        if (claims.get(AUTHORITIES_KEY)==null){
            throw new CustomHttpException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    public Authentication getAuthenticationByRefreshToken(String refreshToken) {
        // decode
        Claims claims = parseClaims(refreshToken);

        // no authority
        if (claims.get(AUTHORITIES_KEY)==null){
            throw new CustomHttpException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Wrong JWT Signature");
        } catch (ExpiredJwtException e) {
            log.info("Expired Token");
        } catch (UnsupportedJwtException e) {
            log.info("Not Supported JWT Token");
        } catch (IllegalArgumentException e) {
            log.info("The Token is invalid");
        }
        return false;
    }

    /**
     * 토큰 만료일 체크
     * @param accessToken
     * @return
     */
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
