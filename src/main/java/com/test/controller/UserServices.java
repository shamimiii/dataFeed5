package com.test.controller;

import com.test.Exception.UserException;
import com.test.model.User;
import com.test.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/")
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") long id)
            throws UserException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("user not found"));
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") long id,
            @RequestBody User updatedUser) throws UserException {

        User user = userRepository.findById(id).orElseThrow(() -> new UserException("user not found for update"));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());

        userRepository.save(user);
        return ResponseEntity.ok().body(user);

    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long id) throws UserException {

        User user = userRepository.findById(id).orElseThrow(() -> new UserException("user not found for delete"));
        userRepository.deleteById(user.getId());
        return ResponseEntity.ok().build();
    }

}
