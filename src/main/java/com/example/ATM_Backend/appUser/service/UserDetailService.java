package com.example.ATM_Backend.appUser.service;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import com.example.ATM_Backend.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService { // UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스

    @Autowired
    private final AppUserRepository appUserRepository;

    public UserDetailService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optional = appUserRepository.findByUsername(username);
        if(optional.isEmpty()) {
            throw new UsernameNotFoundException(username + " 사용자 없음");
        } else {
            AppUser appUser = optional.get();
            return new SecurityUser(appUser);
        }
    }

}
