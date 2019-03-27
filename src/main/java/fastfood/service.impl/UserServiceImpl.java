package fastfood.service.impl;

import fastfood.domain.LoginDTO;
import fastfood.domain.UserResponse;
import fastfood.entity.RoleEntity;
import fastfood.entity.UserEntity;
import fastfood.entity.UserRoleEntity;
import fastfood.repository.RoleRepository;
import fastfood.repository.UserRepository;
import fastfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return new User(user.getUsername(), user.getPassword(), this.getAuthorities(user));
    }
    private Set<SimpleGrantedAuthority> getAuthorities(UserEntity user) {
        Set<SimpleGrantedAuthority> listAuthorites  = new HashSet<>();
        user.getListUserRoles().forEach(listUserRoles -> listAuthorites.add(new SimpleGrantedAuthority("ROLE_" + listUserRoles.getRole().getName())));
        return listAuthorites;
    }



    @Override
    public UserResponse getUserByUserName(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            return  null;
        } else {
            return userEntity.convertToUserResponse();
        }
    }

    @Override
    public UserResponse addUser(LoginDTO loginDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(loginDTO.getPassword()));

        RoleEntity roleEntity = roleRepository.findByName("USER");

        UserEntity savedUser = null;
        if(roleEntity != null ) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(userEntity);
            userRoleEntity.setRole(roleEntity);
            userEntity.setListUserRoles(new HashSet<>(Arrays.asList(userRoleEntity)));
            savedUser = userRepository.save(userEntity);
            return savedUser != null ? savedUser.convertToUserResponse() : null;
        } else {
            savedUser = userRepository.save(userEntity);
            return savedUser != null ? savedUser.convertToUserResponse() : null;
        }
    }

    @Override
    public UserResponse addSale(LoginDTO loginDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(loginDTO.getPassword()));

        RoleEntity roleEntity = roleRepository.findByName("SALE");

        UserEntity savedUser = null;
        if(roleEntity != null ) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(userEntity);
            userRoleEntity.setRole(roleEntity);
            userEntity.setListUserRoles(new HashSet<>(Arrays.asList(userRoleEntity)));
            savedUser = userRepository.save(userEntity);
            return savedUser != null ? savedUser.convertToUserResponse() : null;
        } else {
            savedUser = userRepository.save(userEntity);
            return savedUser != null ? savedUser.convertToUserResponse() : null;
        }
    }

    @Override
    public UserResponse addADMIN(LoginDTO loginDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginDTO.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(loginDTO.getPassword()));

        RoleEntity roleEntity = roleRepository.findByName("ADMIN");

        UserEntity savedUser = null;
        if(roleEntity != null ) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(userEntity);
            userRoleEntity.setRole(roleEntity);
            userEntity.setListUserRoles(new HashSet<>(Arrays.asList(userRoleEntity)));
            savedUser = userRepository.save(userEntity);
            return savedUser != null ? savedUser.convertToUserResponse() : null;
        } else {
            savedUser = userRepository.save(userEntity);
            return savedUser != null ? savedUser.convertToUserResponse() : null;
        }
    }


}
