package ru.tsu.hits.hitsspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.currentTimeMillis;

@RestController
@RequestMapping("/hits")
public class HitsController {

    @GetMapping("/time")
    public String getCurrentTime() {
        return String.valueOf(currentTimeMillis());
    }

    @GetMapping("/me")
    public String getMe(Authentication authentication) {
        return authentication.getName();
    }

    @PreAuthorize("hasAnyAuthority('CAN_POST') or hasAnyAuthority('CAN_POST_2')")
    @PostMapping("/post")
    public String post(Authentication authentication) {
        return authentication.getName() + ": " + currentTimeMillis();
    }

}
