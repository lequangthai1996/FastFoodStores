package fastfood.service.impl;

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
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return new User(user.getUsername(), user.getPassword(), this.getAuthorites(user));
    }
    private Set<SimpleGrantedAuthority> getAuthorites(UserEntity user) {
        Set<SimpleGrantedAuthority> listAuthorites  = new HashSet<>();
        user.getListUserRoles().forEach(listUserRoles -> listAuthorites.add(new SimpleGrantedAuthority("ROLE_" + listUserRoles.getRole().getName())));
        return listAuthorites;
    }
}
