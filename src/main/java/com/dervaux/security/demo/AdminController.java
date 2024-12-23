package com.dervaux.security.demo;

import com.dervaux.security.service.UserService;
import com.dervaux.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/users")
        public Iterable<User> allUsers() {
            return userService.getAllUsers();
        }
    }
