package com.amigoscode.spring_boot_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    record PingPong(String result) {}

    @GetMapping("/ping")
    public PingPong ping() {
        return new PingPong("pong");
    }
}
