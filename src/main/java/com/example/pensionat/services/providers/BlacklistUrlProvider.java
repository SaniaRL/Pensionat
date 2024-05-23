package com.example.pensionat.services.providers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
public class BlacklistUrlProvider {
    @Value("${blacklist.url}")
    private String blacklistUrl;

    @Value("${blacklist.check.url}")
    private String blacklistCheckUrl;

}

