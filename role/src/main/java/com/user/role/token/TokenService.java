package com.user.role.token;

import com.user.role.role.Role;
import com.user.role.user.dto.UserNameRoleDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private final String SECRET_KEY = "13132132121313213212131321321231";
    private final Long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(60);
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    public String generate(final String name, final Role role) {

        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(name)
                .claim("role", role.name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public UserNameRoleDTO getUser(String token) {

        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        final String role = claims.get("role", String.class);
        final String name = claims.getSubject();

        return new UserNameRoleDTO(name, role);
    }

}
