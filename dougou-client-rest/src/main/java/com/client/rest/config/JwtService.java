package com.client.rest.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
@Component
public class JwtService {
    @Value("${jwt.secret}")
    private  String secretKey;

    private  Integer expireDuration = 20*60*1000;
    public  String generateJwt(String username,Object[] authority) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer("dougou").
                withClaim("username",username).
                withArrayClaim("authority", (String[]) authority).
                withClaim("expireDate",new Date(System.currentTimeMillis()+expireDuration)).
                sign(algorithm);
    }
    public  String generateJwt(String username) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withIssuer("dougou").
                withClaim("username",username).
                withClaim("expireDate",new Date(System.currentTimeMillis()+expireDuration)).
                sign(algorithm);
    }
    public String[] getAuthority(String token){
       return JWT.decode(token).getClaim("authority").asArray(String.class);
    }

}
