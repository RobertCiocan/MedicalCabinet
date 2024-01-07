package com.proiect.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET");

    public static boolean validateJwt(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String generateJwt(Long id, String role, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setId(id.toString())
                .setIssuedAt(now)
                .setSubject(subject)
                .setExpiration(new Date(nowMillis + ttlMillis))
                .claim("role", role)
                .signWith(signingKey)
                .compact();
    }
}
