package com.crud.controller;


import com.crud.entity.UserProfile;
import com.crud.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/save")
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createUserProfile(userProfile);
    }

    @GetMapping("/{id}")
    public Optional<UserProfile> getUserProfileById(@PathVariable Long id) {
        return userProfileService.getUserProfileById(id);
    }

    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
        return userProfileService.getAllUserProfiles();
    }

    @PutMapping("/{id}")
    public UserProfile updateUserProfile(@PathVariable Long id, @RequestBody UserProfile updatedProfile) {
        return userProfileService.updateUserProfile(id, updatedProfile);
    }

    @DeleteMapping("/{id}")
    public String deleteUserProfile(@PathVariable Long id) {
        userProfileService.deleteUserProfile(id);
        return "User profile with ID " + id + " deleted successfully.";
    }

}
