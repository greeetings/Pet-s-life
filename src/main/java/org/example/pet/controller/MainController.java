package org.example.pet.controller;

import org.example.pet.domain.Message;
import org.example.pet.domain.User;
import org.example.pet.repos.MessageRepo;
import org.example.pet.service.S3Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    S3Services s3Services;

    @Autowired
    private MessageRepo messageRepo;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    )  {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        }
        else {

            if (file != null && !file.getOriginalFilename().isEmpty()) {
                String path =  s3Services.uploadFile(file.getOriginalFilename(), file);
                message.setFilename(path);
            }

            model.addAttribute("message", null);
            messageRepo.save(message);
        }

        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }


}
