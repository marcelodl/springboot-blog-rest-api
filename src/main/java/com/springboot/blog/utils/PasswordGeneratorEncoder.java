package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGeneratorEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordGeneratorEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordGeneratorEncoder.encode("admin"));
        System.out.println(passwordGeneratorEncoder.encode("marcelo"));
    }
}
