package com.papyrus.logingservice.config;

import com.papyrus.logingservice.documents.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRETKEY = "5266556A586E327234753778214125442A472D4B6150645367566B5970337336";
    public String getEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> getClaims, UserDetails entity){
        return Jwts
                .builder()
                .setClaims(getClaims)
                .setSubject(entity.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails entity){
        return generateToken(new HashMap<>(), entity);
    }

    public Boolean isTokenValid(String token, UserDetails entity){
        final String userName = getEmail(token);
        return (userName.equals(entity.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaims(token);

        return claimResolver.apply(claims);
    }

    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRETKEY);

        return Keys.hmacShaKeyFor(keyByte);
    }
}
