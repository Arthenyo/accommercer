package com.arthenyo.accommerce.projections;

public interface UserDetailsProjection {

    String getUsername();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}

