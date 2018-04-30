package com.example.cloud_client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CloudClientRestController {

    private final CloudClientProperties cloudClientProperties;

    @ResponseBody
    @GetMapping(path = "/foo", produces = MediaType.TEXT_PLAIN_VALUE)
    public String foo() {
        return cloudClientProperties.getFoo();
    }

    @ResponseBody
    @GetMapping(path = "/local", produces = MediaType.TEXT_PLAIN_VALUE)
    public String local() {
        return cloudClientProperties.getLocal();
    }
}
