package com.projeto.academia.controller;

import com.projeto.academia.models.User;
import com.projeto.academia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestHeader("Authorization") String authorization,
                                        @RequestBody User user) {

        user.setRoles("USER");
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id,
                                        @RequestBody User user) {

        userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {

        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<User> users = userService.findAllUser();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id) {

        User user = userService.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
