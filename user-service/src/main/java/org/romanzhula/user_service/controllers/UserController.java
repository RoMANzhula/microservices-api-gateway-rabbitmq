package org.romanzhula.user_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{user-id")
    public ResponseEntity<List<UserResponse>> getUserById(
            @PathVariable("user-id") String userId
    ) {
        return ResponseEntity.ok(userService.getUserByUd(userId));
    }

}
