package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {


    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.expiration-time}")
    private int expirationTime;



    public String generateToken(User user) {
        return Jwts
                .builder()
                .signWith(getSignInKey())
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Date expirationDate = extractExpiration(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T > claimsResolver) {
        Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }


    private Claims extractAllClaims(String token) {

        return Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();


    }


    private SecretKey getSignInKey() {

        byte[] decodedKey = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(decodedKey);
    }


}
