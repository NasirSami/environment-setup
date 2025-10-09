package com.example.controllerdemo.controller;

import com.example.controllerdemo.exception.UserNotFoundException;
import com.example.controllerdemo.model.User;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    public UserController() {
        addUser(new User(null, "John", "Doe", "john.doe@example.com"));
        addUser(new User(null, "Jane", "Smith", "jane.smith@example.com"));
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @GetMapping("/{id}/entity")
    public ResponseEntity<User> getUserAsResponseEntity(@PathVariable Long id) {
        return ResponseEntity.ok(getUser(id));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    private void addUser(User user) {
        long id = idSequence.incrementAndGet();
        user.setId(id);
        users.put(id, user);
    }
}
