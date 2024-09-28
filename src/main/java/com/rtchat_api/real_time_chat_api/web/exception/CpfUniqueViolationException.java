package com.rtchat_api.real_time_chat_api.web.exception;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String message){
        super(message);
    }
}
