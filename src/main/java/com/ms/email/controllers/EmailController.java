package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.Email;
import com.ms.email.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        Email Email = new Email();
        BeanUtils.copyProperties(emailDto, Email);
        emailService.sendEmail(Email);
        return new ResponseEntity<>(Email, HttpStatus.CREATED);
    }

}