package com.news.projectsmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.news.projectsmanagement.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        // User user = getUser(() -> userService.getByEmail(email));
        // return user;
        return userService.getByEmail(email).get();

    }

    public UserDetails getUserById(Long id) {
        return userService.getByUserId(id).get();
    }

    // private User getUser(Supplier<Optional<User>> supplier) {
    // return supplier.get().orElseThrow(() -> new UsernameNotFoundException("User
    // not found"));

    // }

}
