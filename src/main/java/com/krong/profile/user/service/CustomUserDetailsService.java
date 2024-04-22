package com.krong.profile.user.service;

import com.krong.profile.user.model.UserDto;
import com.krong.profile.user.store.UserStore;
import com.krong.profile.user.store.orm.UserJpo;
import com.krong.profile.user.store.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserStore userStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userStore.findBy(username);
        return createUserDetails(userDto.getEmail(), userDto.getPassword());
    }

    private UserDetails createUserDetails(String userEmail, String password) {
        //FIXME password 암호화 시 암호 해제 하는 부분
        return User.builder()
                .username(userEmail)
                .password(password)
                .build();
    }
}
