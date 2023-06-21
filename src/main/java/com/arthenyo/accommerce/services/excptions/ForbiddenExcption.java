package com.arthenyo.accommerce.services.excptions;

public class ForbiddenExcption extends RuntimeException{

    public ForbiddenExcption(String msg){
        super(msg);
    }
}
