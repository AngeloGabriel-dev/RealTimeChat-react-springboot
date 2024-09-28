package com.rtchat_api.real_time_chat_api.exception;

public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException(String message){
        super(message);
    }
}
