package com.news.projectsmanagement.view.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.news.projectsmanagement.model.Users;
import com.news.projectsmanagement.service.UserService;
import com.news.projectsmanagement.view.model.user.LoginRequest;
import com.news.projectsmanagement.view.model.user.LoginResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Users> get(@PathVariable("id") Long id) {
        return userService.getByUserId(id);
    }

    @PostMapping
    public Users add(@RequestBody Users user) {
        return userService.add(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }

}
