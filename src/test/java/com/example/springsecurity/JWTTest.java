package com.example.springsecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class JWTTest {

    @Test
    public void test() {

        HashMap<String, Object> map = new HashMap<>();

        LocalDate localDate = LocalDate.now().plusDays(1);
        String token = JWT.create()
                .withSubject("admin")
                .withIssuer("nividien")
                .withClaim("authority", "user,admin")
                .withExpiresAt(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC256("secretKey"));

        System.out.println(token);
        DecodedJWT decode = JWT.decode(token.substring(0, token.length() -2));
        String payload = decode.getPayload();

        byte[] bytes = Base64.decodeBase64(payload);
        System.out.println(new String(bytes));
    }
}
