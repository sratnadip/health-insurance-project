package com.crud.serviceimpl;

import com.crud.entity.User;
import com.crud.repository.UserRepository;
import com.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User createUser(User user) {

        return repository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return repository.findById(userId).get();

    }

    @Override
    public List<User> getAllUsers() {

        return repository.findAll();
    }

    @Override
    public User updateUser(Long userId, User user) {
        Optional<User> users = repository.findById(userId);
        if (users.isPresent()) {
            User user1 = users.get();
            user1.setUserName(user.getUserName());
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            user1.setRole(user.getRole());
            return repository.save(user1);
        } else {
            return null;
        }

    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> users = repository.findById(userId);
        if (users.isPresent()) {
            repository.deleteById(userId);
        }
    }

    @Override
    public User loginUser(String email, String password) {
        Optional<User> user = repository.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }


    }
}
