package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.DTO.*;
import com.arthenyo.accommerce.entities.Category;
import com.arthenyo.accommerce.entities.Product;
import com.arthenyo.accommerce.entities.Role;
import com.arthenyo.accommerce.entities.User;
import com.arthenyo.accommerce.projections.UserDetailsProjection;
import com.arthenyo.accommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO insert(UserDTO dto){
        User entity =new User();
        copyDtoToEntity(dto, entity);
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

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

    protected User authenticated(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");
            return  userRepository.findByEmail(username).get();
        }catch (Exception e){
            throw new UsernameNotFoundException("Email not faund");
        }
    }

    @Transactional(readOnly = true)
    public UserMinDTO getMe(){
        User user = authenticated();
        return new UserMinDTO(user);
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setPhone(dto.getPhone());
        for (RoleDTO roleDTO: dto.getRoles()){
            Role role = new Role();
            role.setId(roleDTO.getId());
            entity.getRoles().add(role);
        }
    }
}
