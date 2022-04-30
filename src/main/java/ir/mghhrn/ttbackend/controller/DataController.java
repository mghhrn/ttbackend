package ir.mghhrn.ttbackend.controller;

import ir.mghhrn.ttbackend.service.TherapySessionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class DataController {

    private final TherapySessionService therapySessionService;

    @GetMapping("/therapy-session/all")
    public String getAllData(Model model) {
        model.addAttribute("thsList", therapySessionService.findAll());
        return "session";
    }
}
