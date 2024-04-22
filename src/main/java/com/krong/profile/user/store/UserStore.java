package com.krong.profile.user.store;

import com.krong.profile.user.model.UserDto;
import com.krong.profile.user.store.orm.UserJpo;
import com.krong.profile.user.store.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserStore {
    //
    private final UserRepository userRepository;

    public void save(UserDto user, String password) {
        //
        this.userRepository.save(new UserJpo(user, password));
    }

    public UserDto findBy(String email) {
        Optional<UserJpo> jpo = this.userRepository.findByEmail(email);
        if (jpo.isEmpty())
            throw new NoSuchElementException("User not found");
        return jpo.get().toDomain();
    }
}
