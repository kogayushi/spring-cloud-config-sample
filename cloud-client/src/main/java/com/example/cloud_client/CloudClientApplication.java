package com.example.cloud_client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@SpringBootApplication
public class CloudClientApplication {

    private final CloudClientProperties cloudClientProperties;

    @RequestMapping("/info")
    public String greeting() {
        return cloudClientProperties.getFoo();
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudClientApplication.class, args);
    }
}
