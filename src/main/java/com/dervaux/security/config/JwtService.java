package com.dervaux.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "Xi4VUHmxzXOAQQu8cqGQHaYl9PQWHS9cMVJr2NZbbZ8AxdObPeTP3/dW3FkK7lnZ9vRiBpnPF+UZcY0YWemMBNpCFVVnRZP2tWsL2D7oAt+jhd1Rtq91OTAUcx5eT2EveAztcHxjLVMnZQFhSqCaqArV/K+yz1GXXCEP5rY+sF7cc5zBPbg3AOAVBSF+ckk5N8mFGW1xxSfWWI/+adqI6UY0xyb7ToyDNKnnzO2kt75OBaJ2B24OlVY/m7JsGdHYSttUDvGFJot6Kx+V2dgQ0sgPQtR5UZaMc+5mykBx5wnWWpNc6NGgyEtV9Sor8ZWOVZPF3DYIoUPL3yzgFLuSIShVo5blJNoY6gNI6s9NR7c=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extratExpiration(token).before(new Date());
    }

    private Date extratExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
