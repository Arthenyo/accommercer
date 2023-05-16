package com.arthenyo.accommerce.services.excptions;

public class DataBaseExcption extends RuntimeException{

    public DataBaseExcption(String msg){
        super(msg);
    }
}
