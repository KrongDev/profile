package com.krong.profile.user.service;

import com.krong.profile.config.JwtTokenProvider;
import com.krong.profile.config.model.TokenInfo;
import com.krong.profile.user.model.UserDto;
import com.krong.profile.user.store.UserStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    //
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserStore userStore;

    public String signUp(String name, String email, String password) {
        String newId = UUID.randomUUID().toString();
        UserDto user = UserDto.builder()
                .id(newId)
                .username(name)
                .email(email)
                .createdAt(System.currentTimeMillis())
                .build();
        //FIXME password의 암호화 추가
        this.userStore.save(user, passwordEncoder.encode(password));
        return newId;
    }

    public TokenInfo login(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }
}
