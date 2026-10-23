package com.salon.userservice.service;

import com.salon.userservice.exception.UserException;
import com.salon.userservice.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id) throws UserException;
    User updateUser(User user, Long id) throws UserException;
    void deleteUser(Long id) throws UserException;
    User getUserByJwt(String jwt) throws Exception;
    User updateUserRole(Long id, String role);

}
