package com.scaler.bloggapp.users;

import com.scaler.bloggapp.users.dtos.CreateUserRequest;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    public final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserEntity createUser(CreateUserRequest req) {
        var newUser = UserEntity.builder()
                .username(req.getUsername())
//                .password(req.getPassword())
                .email(req.getEmail())
                .build();
        return usersRepository.save(newUser);

    }

    public UserEntity getUser(String username) {

        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity loginUser(String username, String password) {
        var user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        //todo: check the password
        return user;
    }

    public UserEntity getUser(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {

            super("User with username: " + username + " not found");
        }

        public UserNotFoundException(Long userId) {
            super("User with id: " + userId + " not found");
        }
    }
}
