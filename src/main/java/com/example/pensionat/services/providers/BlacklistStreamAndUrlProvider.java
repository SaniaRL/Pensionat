package com.example.pensionat.services.providers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Getter
@Service
public class BlacklistStreamAndUrlProvider {
    @Value("${blacklist.url}")
    private String blacklistUrl;

    @Value("${blacklist.check.url}")
    private String blacklistCheckUrl;

    public InputStream getDataStream() throws IOException {
        URL url = new URL(blacklistUrl);
        return url.openStream();
    }
}

