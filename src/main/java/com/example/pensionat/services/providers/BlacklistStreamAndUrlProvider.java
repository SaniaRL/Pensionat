package com.example.pensionat.services.providers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    public HttpResponse<String> getHttpResponse(String email) throws IOException, InterruptedException {
        String blacklistApiUrl= getBlacklistCheckUrl();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(blacklistApiUrl + email))
                .GET()
                .build();

        return client.send(request,HttpResponse.BodyHandlers.ofString());
    }
}

