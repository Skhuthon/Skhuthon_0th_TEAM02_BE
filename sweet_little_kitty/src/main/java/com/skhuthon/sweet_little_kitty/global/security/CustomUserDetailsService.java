package com.skhuthon.sweet_little_kitty.global.security;

import com.skhuthon.sweet_little_kitty.app.entity.user.User;
import com.skhuthon.sweet_little_kitty.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), "", user.getAuthorities());
    }
}
