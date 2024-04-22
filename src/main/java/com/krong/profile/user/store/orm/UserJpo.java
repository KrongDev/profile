package com.krong.profile.user.store.orm;

import com.krong.profile.user.model.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserJpo {
    //
    @Id
    private String id;
    @Version
    private int entityVersion;
    private String username;
    private String email;
    private String password;

    private long createdAt;
    private long expirationAt;

    public UserJpo(UserDto userDto, String password) {
        this.id = userDto.getId();
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = password;
        this.createdAt = userDto.getCreatedAt();
        this.expirationAt = userDto.getExpirationAt();
    }

    public UserDto toDomain() {
        //
        return UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
