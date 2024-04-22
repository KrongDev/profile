package com.krong.profile.user.controller;

import com.krong.profile.config.model.TokenInfo;
import com.krong.profile.user.command.SignUpCommand;
import com.krong.profile.user.model.LoginDto;
import com.krong.profile.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {
    //
    private final UserService userService;

    //유저 로그인 부분
    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpCommand command) {
        //
        command.validate();
        String username = command.getUsername();
        String email = command.getEmail();
        String password = command.getPassword();

        return userService.signUp(username, email, password);
    }

    //유저 로그인 부분
    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto) {

        return userService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    //유저 데이터 호출 부분
    @GetMapping("/say")
    @Secured("user")
    public String hello() {
        return "hello";
    }
}
