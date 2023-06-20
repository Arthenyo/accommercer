package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.entities.Role;
import com.arthenyo.accommerce.entities.User;
import com.arthenyo.accommerce.projections.UserDetailsProjection;
import com.arthenyo.accommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if(result.size() == 0){
            throw new UsernameNotFoundException("User not faund");
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection projection: result){
            user.addRole(new Role(projection.getRoleId(),projection.getAuthority()));
        }
        return user;
    }
}
