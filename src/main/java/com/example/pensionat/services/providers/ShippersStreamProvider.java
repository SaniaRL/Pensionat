package com.example.pensionat.services.providers;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class ShippersStreamProvider {
    public InputStream getDataStream() throws IOException {
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        return url.openStream();
    }
}
