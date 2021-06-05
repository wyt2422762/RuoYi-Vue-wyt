package com.ruoyi.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * @author Administrator
 */
public class LoginMpUser extends LoginUser {

    private String phoneNumber;

    private String password;

    public LoginMpUser(String phoneNumber, String password, Set<String> permissions){
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.setPermissions(permissions);
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return phoneNumber;
    }



}
