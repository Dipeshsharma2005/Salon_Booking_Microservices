package com.salon.userservice.service.implementation;

import com.salon.userservice.domain.UserRole;
import com.salon.userservice.exception.UserException;
import com.salon.userservice.model.User;
import com.salon.userservice.payload.dto.KeyCloakUserDTO;
import com.salon.userservice.repository.UserRepository;
import com.salon.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("user not found with id " + id);
    }

    @Override
    public User updateUser(User user, Long id) throws UserException {
        Optional<User> user1 = userRepository.findById(id);
        if (user1.isEmpty()) {
            throw new UserException("user not found with id " + id);
        }

        User existingUser = user1.get();

        existingUser.setFullName(user.getFullName());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException("user not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    private final KeyCloakService keyCloakService;

    @Override
    public User getUserByJwt(String jwt) throws Exception {
        String token = jwt.replace("Bearer ", ""); // remove prefix
        KeyCloakUserDTO keyCloakUserDTO = keyCloakService.fetchUserProfileByJwt(token);

        return userRepository.findByEmail(keyCloakUserDTO.getEmail());
    }
    @Override
    public User updateUserRole(Long id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            // Convert string role to enum (optional if your field is enum)
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            user.setRole(userRole);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }

        return userRepository.save(user);
    }

}

