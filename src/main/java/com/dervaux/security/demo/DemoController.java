package com.dervaux.security.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Hello from admin endpoint");
    }

    @Secured("ROLE_USER")
    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("Hello from user endpoint");
    }

}


