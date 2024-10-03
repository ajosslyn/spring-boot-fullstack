package com.amigoscode.spring_boot_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    public static record PingPong(String result) {}

    @GetMapping("/ping")
    public PingPong getPingPong() {
        return new PingPong("***PONG***");
    }
}