package com.example.springsecurity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    public static String encode(String subject) {
        LocalDate localDate = LocalDate.now().plusDays(1);

        String token = JWT.create()
                .withSubject(subject)
                .withIssuer("nividien")
                .withClaim("authority", "ROLE_USER,ROLE_ADMIN")
                .withExpiresAt(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256("secretKey"));
        return token;
    }

    private static final ObjectMapper objectMapper= new ObjectMapper();

    public static Map decode(String token) throws IOException {
        DecodedJWT decode = JWT.decode(token);
        String payload = decode.getPayload();
        byte[] bytes = Base64.decodeBase64(payload);
        return objectMapper.readValue(bytes, Map.class);
    }


}
