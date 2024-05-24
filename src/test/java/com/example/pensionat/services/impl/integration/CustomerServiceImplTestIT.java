package com.example.pensionat.services.impl.integration;

import com.example.pensionat.repositories.CustomerRepo;
import com.example.pensionat.services.impl.CustomerServiceImpl;
import com.example.pensionat.services.interfaces.CustomerService;
import com.example.pensionat.services.providers.BlacklistStreamAndUrlProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerServiceImplTestIT {

    CustomerService sut;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private BlacklistStreamAndUrlProvider provider;
    String email = "mail@mail.com";

    @BeforeEach()
    void setup() {
        sut = new CustomerServiceImpl(customerRepo, provider);
    }

    @Test
    void getBlacklistedToArrayWillFetch() throws IOException {
        Scanner s = new Scanner(provider.getDataStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("name"));
        assertTrue(result.contains("group"));
        assertTrue(result.contains("created"));
        assertTrue(result.contains("ok"));
    }

    @Test
    void checkIfEmailBlacklistedWillFetch() throws IOException, InterruptedException {
        HttpResponse<String> response = provider.getHttpResponse(email);

        assertTrue(response.body().contains("statusText"));
        assertTrue(response.body().contains("ok"));
    }
}