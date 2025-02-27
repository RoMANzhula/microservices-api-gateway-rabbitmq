package org.romanzhula.user_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.user_service.requests.UserRequest;
import org.romanzhula.user_service.responses.UserResponse;
import org.romanzhula.user_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable("user-id") String userId
    ) {
        return ResponseEntity.ok(userService.getUserByUd(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(
            @RequestBody UserRequest userRequest
    ) {
        return ResponseEntity.ok(userService.addNewUser(userRequest));
    }

}
