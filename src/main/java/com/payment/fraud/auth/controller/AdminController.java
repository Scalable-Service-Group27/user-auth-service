package com.payment.fraud.auth.controller;

import com.payment.fraud.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/permissions/{userId}")
    public ResponseEntity<Set<String>> getUserPermissions(@PathVariable Long userId) {
        Set<String> permissions = userService.getUserPermissions(userId);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping("/permissions/assign")
    public ResponseEntity<String> assignPermission(
            @RequestParam Long userId,
            @RequestParam String permission) {
        userService.assignPermission(userId, permission);
        return ResponseEntity.ok("Permission assigned successfully");
    }

    @PostMapping("/permissions/remove")
    public ResponseEntity<String> removePermission(
            @RequestParam Long userId,
            @RequestParam String permission) {
        userService.removePermission(userId, permission);
        return ResponseEntity.ok("Permission removed successfully");
    }
}
