package com.krong.profile.user.model;

import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String username;
    private String password;

    public void validate() {
        Assert.hasText(username, "Not Have User Name!");
        Assert.hasText(password, "Not Have Password!");
    }
}
