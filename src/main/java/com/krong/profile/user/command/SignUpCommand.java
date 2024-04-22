package com.krong.profile.user.command;

import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpCommand {
    //
    private String username;
    private String email;
    private String password;

    public void validate() {
        Assert.hasText(username, "No username provided");
        Assert.hasText(email, "No email provided");
        Assert.hasText(password, "No password provided");
    }
}
