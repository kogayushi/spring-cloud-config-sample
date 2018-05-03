package com.example.cloud_client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Data
@Component
@ConfigurationProperties(prefix = "info")
public class CloudClientProperties {

    @NotNull
    private String foo;

    private String local;
}
