package com.shangxian.art.ioc;

public class IocException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public IocException() {
    }

    public IocException(String msg) {
        super(msg);
    }
}
