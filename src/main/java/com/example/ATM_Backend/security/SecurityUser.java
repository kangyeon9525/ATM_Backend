package com.example.ATM_Backend.security;

import com.example.ATM_Backend.appUser.model.AppUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private AppUser appUser;

    public SecurityUser(AppUser appUser) {
        super(appUser.getId().toString(), appUser.getPassword(),
                AuthorityUtils.createAuthorityList(appUser.getRole().toString()));
        this.appUser = appUser;
    }

    public AppUser getAppUser() {
        return appUser;
    }
}
