package org.example.pet.controller;

import org.example.pet.domain.User;
import org.example.pet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {


    @Autowired
    private UserService userService;



    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {




        boolean isConfirm = StringUtils.isEmpty(passwordConfirm);
        if (isConfirm) {
            model.addAttribute("password2Error","\"Password confirmation cannot be empty\"");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            bindingResult.addError(new FieldError("user", "password", "Passwords are different!"));
        }

        if (isConfirm || bindingResult.hasErrors() ) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }


}
