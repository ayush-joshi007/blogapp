package com.scaler.bloggapp.users;

import com.scaler.bloggapp.users.dtos.CreateUserRequest;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    public final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    public UserEntity createUser(CreateUserRequest req) {
        UserEntity newUser = modelMapper.map(req, UserEntity.class);
        // todo: encrypt and save password
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
