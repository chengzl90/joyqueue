package com.jd.journalq.store;

/**
 * @author liyue25
 * Date: 2018/9/3
 */
public class StoreInitializeException extends RuntimeException {
    StoreInitializeException(){super();}
    public StoreInitializeException(String message){super(message);}
    StoreInitializeException(String message,Throwable t){super(message,t);}
}
