package com.crud.controller;

import com.crud.dto.LoginDto;
import com.crud.entity.User;
import com.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/save")
    public User creteUser(@RequestBody User user){

        return service.createUser(user);
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return service.getAllUsers();

    }

   @GetMapping("{userId}")
    public User getUserById(@PathVariable Long id){
        return service.getUserById(id);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return service.updateUser(id,user);

    }

    @DeleteMapping("delete/{userId}")
    public String deleteUser(@PathVariable Long userId){
        service.deleteUser(userId);
        return "user deleted successfully";

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
       User user = service.loginUser(loginDto.getEmail(), loginDto.getPassword());

        if (user != null) {
            // Return only selected fields
            LoginDto responseDto = new LoginDto(user.getUserId(),
                    user.getEmail(),user.getPassword(),user.getRole());
            return ResponseEntity.ok(responseDto);
        } else
            {    return ResponseEntity.status(401).body("Invalid credentials");}


}}




