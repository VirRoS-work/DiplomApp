package com.virros.diplom.controller;

import com.virros.diplom.message.request.LoginForm;
import com.virros.diplom.message.request.SignUpForm;
import com.virros.diplom.message.request.SignUpFormApplicant;
import com.virros.diplom.message.request.SignUpFormCompany;
import com.virros.diplom.message.response.JwtResponse;
import com.virros.diplom.model.*;
import com.virros.diplom.repository.ApplicantRepository;
import com.virros.diplom.repository.EmployerRepository;
import com.virros.diplom.repository.RoleRepository;
import com.virros.diplom.repository.UserRepository;
import com.virros.diplom.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    private static final Logger logger = LoggerFactory.getLogger(AuthRestAPIs.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );
        Set<String> roles = new HashSet<>();
        for (Role role : user.getRoles()){
            roles.add(role.getName().name());
        }
        return ResponseEntity.ok(new JwtResponse(jwt, username, roles));
    }

    @PostMapping("/signupcompany")
    public ResponseEntity<String> registerCompany(@Valid @RequestBody SignUpFormCompany signUpRequest) {

        logger.info(signUpRequest.toString());

        if(userRepository.existsByUsername(signUpRequest.getSignUpForm().getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpRequest.getSignUpForm().getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        User user = registerUser(signUpRequest.getSignUpForm());

        //register company

        Employer employer = new Employer(signUpRequest.getName(), signUpRequest.getType(), signUpRequest.getCount(),
                signUpRequest.getAddress(), signUpRequest.getSite(), signUpRequest.getDescription(), user, null, null, null);

        employerRepository.save(employer);

        return ResponseEntity.ok().body("Company registered successfully!");
    }

    @PostMapping("/signupapplicant")
    public ResponseEntity<?> registerApplicant(@Valid @RequestBody SignUpFormApplicant signUpRequest) {

        logger.info(signUpRequest.toString());

        if(userRepository.existsByUsername(signUpRequest.getSignUpForm().getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpRequest.getSignUpForm().getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        User user = registerUser(signUpRequest.getSignUpForm());

        //register applicant

        Applicant applicant = new Applicant("Закрытый", signUpRequest.getFirst_name(), signUpRequest.getLast_name(),
                signUpRequest.getFather_name(), signUpRequest.getSex(), signUpRequest.getDate_of_birth(), user);

        logger.info(applicant.toString());

        applicantRepository.save(applicant);

        return ResponseEntity.ok().body("Applicant register successfully!");
    }

    private User registerUser(SignUpForm signUpRequest) {
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "company":
                    Role companyRole = roleRepository.findByName(RoleName.ROLE_COMPANY)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(companyRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        return userRepository.save(user);
    }

}
