package org.example.pet.controller;

import org.example.pet.domain.Message;
import org.example.pet.domain.User;
import org.example.pet.repos.MessageRepo;
import org.example.pet.service.S3Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Controller
@RequestMapping("/user-messages/{user}")
public class EditMessageController {
    @Autowired
    S3Services s3Services;
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Message message
    ) {

        Set<Message> messages = user.getMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));


        return "userMessages";
    }

    @PostMapping
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("file")MultipartFile file
            ) {

        if (message == null || currentUser == null) {

        } else {
            if (message.getAuthor().equals(currentUser)) {
                if (!StringUtils.isEmpty(text)) {
                    message.setText(text);
                }


                if (file != null && !file.getOriginalFilename().isEmpty()) {
                    String path =  s3Services.uploadFile(file.getOriginalFilename(), file);
                    message.setFilename(path);
                }
                messageRepo.save(message);

            }
        }
        return "redirect:/user-messages/" + user;
    }
}
