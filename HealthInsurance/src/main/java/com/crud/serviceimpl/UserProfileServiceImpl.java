package com.crud.serviceimpl;

import com.crud.entity.User;
import com.crud.entity.UserProfile;
import com.crud.repository.UserProfileRepository;
import com.crud.repository.UserRepository;
import com.crud.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public Optional<UserProfile> getUserProfileById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    @Override
    public UserProfile updateUserProfile(Long id, UserProfile updatedProfile) {
        UserProfile existingProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User profile not found with ID: " + id));

        // Update only existing fields in the entity
        existingProfile.setName(updatedProfile.getName());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setPassword(updatedProfile.getPassword());
        existingProfile.setPhone(updatedProfile.getPhone());
        existingProfile.setDob(updatedProfile.getDob());
        existingProfile.setGender(updatedProfile.getGender());
        existingProfile.setCorrespondenceAddress(updatedProfile.getCorrespondenceAddress());
        existingProfile.setPermanentAddress(updatedProfile.getPermanentAddress());
        existingProfile.setMaritalStatus(updatedProfile.getMaritalStatus());
        existingProfile.setOccupation(updatedProfile.getOccupation());
        existingProfile.setBloodGroup(updatedProfile.getBloodGroup());
        existingProfile.setEmergencyContact(updatedProfile.getEmergencyContact());
        existingProfile.setAadhaarNumber(updatedProfile.getAadhaarNumber());

        return userProfileRepository.save(existingProfile);
    }

    @Override
    public void deleteUserProfile(Long id) {
        userProfileRepository.deleteById(id);
    }

    @Override
    public UserProfile getProfileByUserId(Long userId) {
        return userProfileRepository.findByUser_UserId(userId)
                .orElse(null); // or throw exception if needed
    }

    @Override
    public UserProfile createProfileWithUserId(Long userId, UserProfile profile) {
        return userRepository.findById(userId).map(user -> {
            profile.setUser(user);
            user.setUserProfile(profile); // maintain bidirectional mapping if needed
            return userProfileRepository.save(profile);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}
