package com.example.pensionat.services.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class ShippersStreamProvider {

    @Value("${shippers.url}")
    private String shippersUrl;

    public InputStream getDataStream() throws IOException {
        URL url = new URL(shippersUrl);
        return url.openStream();
    }
}
