package com.client.rest.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtService {
    @Value("${jwt.secret}")
    private static String secretKey;
    private static Integer expireDuration = 20*60*1000;
    public static String generateJwt(String username,String authority) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer("dougou").
                withClaim("username",username).
                withClaim("authority",authority).
                withClaim("expireDate",new Date(System.currentTimeMillis()+expireDuration)).
                sign(algorithm);
    }
    public static String generateJwt(String username) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer("dougou").
                withClaim("username",username).
                withClaim("expireDate",new Date(System.currentTimeMillis()+expireDuration)).
                sign(algorithm);
    }

}
