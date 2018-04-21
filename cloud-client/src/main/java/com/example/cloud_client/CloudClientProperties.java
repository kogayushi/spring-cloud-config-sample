package com.example.cloud_client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "info")
public class CloudClientProperties {

    private String foo;
}
