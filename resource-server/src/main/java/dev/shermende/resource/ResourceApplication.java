package dev.shermende.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ResourceApplication {

    public static void main(String... args) {
        SpringApplication.run(ResourceApplication.class, args);
    }

    @GetMapping("/user")
    public String allow(
        @AuthenticationPrincipal Authentication authentication
    ) {
        return authentication.getPrincipal().toString();
    }

    @GetMapping("/admin")
    public String deny(
        @AuthenticationPrincipal Authentication authentication
    ) {
        return authentication.getPrincipal().toString();
    }

}