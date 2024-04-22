package com.krong.profile.user.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private long createdAt;
    private long expirationAt;

    public UserDto() {
        this.createdAt = System.currentTimeMillis();
    }
}
