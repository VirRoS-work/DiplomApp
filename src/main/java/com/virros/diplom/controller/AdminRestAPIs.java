package com.virros.diplom.controller;

import com.virros.diplom.message.request.LoginForm;
import com.virros.diplom.model.Employer;
import com.virros.diplom.model.User;
import com.virros.diplom.repository.EmployerRepository;
import com.virros.diplom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminRestAPIs {

    private static final Logger logger = LoggerFactory.getLogger(AuthRestAPIs.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    MyMailSender mailSender;

    @GetMapping("/usernames")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsernames() {

        List<String> usernames = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            usernames.add(user.getUsername());
        }

        return ResponseEntity.ok().body(usernames);
    }

    @PutMapping("/reset_password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resetPasswordToUser(@RequestBody LoginForm form) {

        User user = userRepository.findByUsername(form.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found Exception!"));

        user.setPassword(encoder.encode(form.getPassword()));

        userRepository.save(user);
        mailSender.sendMailResetPasswordNotification(user.getEmail());

        return ResponseEntity.ok().body("User password changed successfully!");

    }

    @GetMapping("/blocked_companies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBlockedCompanies() {

        return ResponseEntity.ok().body(employerRepository.findAllByBlockedUser());
    }

    @PutMapping("/company/unlock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unlockCompany(@PathVariable Long id) {

        Employer employer = employerRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Employer not found exception!"));

        User user = employer.getUser();
        user.setBlocked(false);
        userRepository.save(user);

        mailSender.sendMailSuccessfullyRegistrationNotification(employer);

        return ResponseEntity.ok().body("Company unlock!");
    }

    @PutMapping("/company/block/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockCompany(@PathVariable Long id) {

        Employer employer = employerRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Employer not found exception!"));

        mailSender.sendMailErrorRegistrationNotification(employer);

        employerRepository.delete(employer);

        return ResponseEntity.ok().body("Company blocked!");
    }
}
