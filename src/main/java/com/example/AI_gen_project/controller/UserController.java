package com.example.AI_gen_project.controller;

import com.example.AI_gen_project.entity.User;
import com.example.AI_gen_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<User> resetPassword(
            @PathVariable Long id,
            @RequestBody String newPassword) {
        return ResponseEntity.ok(userService.resetPassword(id, newPassword));
    }

    @PatchMapping("/{id}/toggle-lock")
    public ResponseEntity<User> toggleLocked(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleLocked(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
