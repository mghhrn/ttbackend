package ir.mghhrn.ttbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    @GetMapping
    public ResponseEntity<String> sample() {
        return ResponseEntity.ok("Hello there");
    }
}
