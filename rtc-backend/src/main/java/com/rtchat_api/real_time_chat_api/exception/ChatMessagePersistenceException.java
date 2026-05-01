package com.rtchat_api.real_time_chat_api.exception;

public class ChatMessagePersistenceException extends RuntimeException{
    public ChatMessagePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
