package com.example.schedules.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component// 스프링 빈으로 등록하여, 다른 클래스에서 쉽게 주입(@Autowired)해서 사용할 수 있게 함.
public class PasswordEncoder {

    public String encode(String rawPassword) {
        //입력한 비밀번호를 BCrypt 알고리즘으로 암호화해 문자열로 반환
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        //입렵한 비밀번호가 암호화된 비밀번호와 일치하는지 확인
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}