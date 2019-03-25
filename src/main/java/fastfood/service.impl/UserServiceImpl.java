package fastfood.service.impl;

import fastfood.domain.UserResponse;
import fastfood.entity.UserEntity;
import fastfood.repository.UserRepository;
import fastfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

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
        UserEntity userEntity =userRepository.findByUsername(username);
        if(userEntity != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(userEntity.getUsername());
            userResponse.setFullName(userEntity.getFullName());
            userResponse.getAvatar(userEntity.getAvatar());
            userResponse
        }
    }
}
