package com.example.springsecurity;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CommonTest {

    @Test
    public void testEncode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        String encode = encoder.encode("123");
        System.out.println(encode);
    }

    @Test
    public void test() {
        System.out.println(Object.class.isAssignableFrom(String.class));
    }
}
