package com.shopsphere.shopsphere.security.services;

import com.shopsphere.shopsphere.domain.models.Authority;
import com.shopsphere.shopsphere.domain.models.User;
import com.shopsphere.shopsphere.repositories.AuthorityRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {


    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.expiration-time}")
    private int expirationTime;

    private AuthorityRepository authorityRepository;

    public JwtService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }



    public String generateToken(User user) {

        Set<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        System.out.println(authorities);

        return Jwts
                .builder()
                .signWith(getSignInKey())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("authorities", authorities)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

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
