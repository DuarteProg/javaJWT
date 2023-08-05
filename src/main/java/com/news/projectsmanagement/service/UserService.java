package com.news.projectsmanagement.service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.news.projectsmanagement.model.Users;
import com.news.projectsmanagement.repository.UserRepository;
import com.news.projectsmanagement.security.JWTService;
import com.news.projectsmanagement.view.model.user.LoginResponse;

@Service
public class UserService {

    private static final String headerPrefix = "Bearer ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public Optional<Users> getByUserId(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Users> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users add(Users user) {
        user.setId(null);

        if (getByEmail(user.getEmail()).isPresent()) {
            throw new InputMismatchException("there is a user with the email: " + user.getEmail());
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        return userRepository.save(user);
    }

    public LoginResponse login(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = headerPrefix + jwtService.generateToken(authentication);

        Users user = userRepository.findByEmail(email).orElse(null);

        return new LoginResponse(token, user);
    }

}
