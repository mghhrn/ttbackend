package ir.mghhrn.ttbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/therapy-session")
public class TherapySessionRestController {

    @GetMapping
    public ResponseEntity<Void> get(Principal principal) {
        return ResponseEntity.ok().build();
    }
}
