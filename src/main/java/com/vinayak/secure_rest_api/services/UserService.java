package com.vinayak.secure_rest_api.services;

import com.vinayak.secure_rest_api.dto.UserDTO;
import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.repositories.UserRepositorie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional //Ensures database operations roll back safely if an error occurs
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepositorie userRepo;
    public final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElse(null);
    }

    public User findById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    public User getUserById(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("USER ID NOT FOUND: "+id));
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepo.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO updatedUser) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("USER ID NOT FOUND: " + id));

        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getRoles() != null) {
            existingUser.setRoles(updatedUser.getRoles());
        }
        if (updatedUser.getPermissions() != null) {
            existingUser.setPermissions(updatedUser.getPermissions());
        }
        userRepo.save(existingUser);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    public void deleteUser(Long id){

        if(!userRepo.existsById(id)){
            throw new RuntimeException("CANNOT DELECT. USER ID NOT FOUND: "+id);
        }

        userRepo.deleteById(id);
    }

    //user delete itself
    @Transactional
    public void deleteUser(String username){

        boolean exists = userRepo.existsUserByUsername(username);
        if (!exists) {
            throw new RuntimeException("CANNOT DELETE. USER NOT FOUND: " + username);
        }
        userRepo.deleteByUsername(username);
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElse(null);
    }

    public User save(User newuser){
        return userRepo.save(newuser);
    }

}
