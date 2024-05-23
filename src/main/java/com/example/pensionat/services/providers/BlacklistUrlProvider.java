package com.example.pensionat.services.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BlacklistUrlProvider {

    @Value("${blacklist.url}")
    private String blacklistUrl;

    public String getBlacklistUrl(){
        return this.blacklistUrl;
    }
}

