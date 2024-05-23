package com.example.pensionat.services.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class BlacklistStreamProvider {

    @Value("${blacklist.url}")
    private String blacklistUrl;

    public InputStream getDataStream() throws IOException {
        URL url = new URL(blacklistUrl);
        return url.openStream();
    }
}

