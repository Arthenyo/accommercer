package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.entities.User;
import com.arthenyo.accommerce.services.excptions.ForbiddenExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(Long userId){
        User me = userService.authenticated();
        if(!me.meRole("ROLE_ADMIN") && !me.getId().equals(userId)){
            throw new ForbiddenExcption("Acesso negado");
        }
    }
}
