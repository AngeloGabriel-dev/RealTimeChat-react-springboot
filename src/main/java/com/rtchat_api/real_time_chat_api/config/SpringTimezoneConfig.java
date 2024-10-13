package com.rtchat_api.real_time_chat_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringTimezoneConfig {
    @PostConstruct
    public void timeZoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo")); }
}
