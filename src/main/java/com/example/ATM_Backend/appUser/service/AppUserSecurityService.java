package com.example.ATM_Backend.appUser.service;

import com.example.ATM_Backend.appUser.model.AppUser;
import com.example.ATM_Backend.appUser.model.AppUserRole;
import com.example.ATM_Backend.appUser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppUserSecurityService implements UserDetailsService { // UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
    private final AppUserRepository appUserRepository;

    @Override // loadUserByUsername 메서드는 사용자명(username)으로 스프링 시큐리티의 사용자(User) 객체를 조회하여 리턴하는 메서드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> _appUser = this.appUserRepository.findByUsername(username);
        if (_appUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        AppUser appUser = _appUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) { // 사용자명이 ‘admin’인 경우에는 ADMIN 권한(ROLE_ADMIN)을 부여
            authorities.add(new SimpleGrantedAuthority(AppUserRole.ADMIN.getValue()));
        } else { // 사용자명이 'admin'이 아닌 경우에는 USER 권한(ROLE_USER)을 부여
            authorities.add(new SimpleGrantedAuthority(AppUserRole.USER.getValue()));
        }
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

}
