package ir.rashasoft.leavemanagement.controller;

import ir.rashasoft.leavemanagement.emailService.EmailService;
import ir.rashasoft.leavemanagement.login.UserRepository;
import ir.rashasoft.leavemanagement.model.CriticsAndSuggestions;
import ir.rashasoft.leavemanagement.repository.CriticsAndSuggestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class CriticsAndSuggestionsController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CriticsAndSuggestionsRepository repository;
    @Autowired
    EmailService emailService;

    @GetMapping("/criticsAndSuggestions")
    public String criticsAndSuggestionsForm(Model model) {
        model.addAttribute("criticsAndSuggestions", new CriticsAndSuggestions());
        model.addAttribute("user", userRepository.findAll());
        return "critics-and-suggestions";
    }

    @PostMapping("/criticsAndSuggestions")
    public String criticsAndSuggestions(CriticsAndSuggestions criticsAndSuggestions, Model model) {


        emailService.send(criticsAndSuggestions.getUser().getEmail(), criticsAndSuggestions.getSubject(), criticsAndSuggestions.getText());


        return "g";

    }

}
