package com.example.pensionat.services.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class XmlStreamProvider{

    @Value("${contractCustomers.url}")
    private String contractCustomersUrl;

    public InputStream getDataStream() throws IOException {
        URL url = new URL(contractCustomersUrl);
        return url.openStream();
    }
}