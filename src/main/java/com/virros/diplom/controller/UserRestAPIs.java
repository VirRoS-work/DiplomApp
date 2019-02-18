package com.virros.diplom.controller;

import com.virros.diplom.model.*;
import com.virros.diplom.repository.*;
import com.virros.diplom.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserRestAPIs {

    private static final Logger logger = LoggerFactory.getLogger(UserRestAPIs.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    LanguageSkillRepository languageSkillRepository;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    SportSkillRepository sportSkillRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    ContactApplicantRepository contactApplicantRepository;

    @Autowired
    ContactTypeRepository contactTypeRepository;

    @Autowired
    JwtProvider tokenProvider;

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getApplicantForAccount(@RequestHeader(value = "Authorization") String token){

        return ResponseEntity.ok().body(getApplicantByToken(token));
    }

    // Languages

    @GetMapping("/languages")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getLanguagesForAccount(@RequestHeader(value = "Authorization") String token){

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getLanguage_skills());
    }

    @PostMapping("/language")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveLanguageForAccount(@RequestBody LanguageSkill languageSkill,
                                                    @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Language language = languageRepository.findByName(languageSkill.getLanguage().getName()).orElseThrow(
                () -> new UsernameNotFoundException("Language not found exception!")
        );

        languageSkill.setApplicant(applicant);
        languageSkill.setLanguage(language);
        LanguageSkill ls = languageSkillRepository.save(languageSkill);
        return ResponseEntity.ok().body(ls);
    }

    @DeleteMapping("/language/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteLanguageForAccount(@PathVariable Long id,
                                                      @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            LanguageSkill language = languageSkillRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Language Skill not found whit your id!")
            );

            if (!language.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Language Skill does not belong to the applicant.",
                        HttpStatus.BAD_REQUEST);
            }

            languageSkillRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Language Skill deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Language Skill.");
    }

    // Sports

    @GetMapping("/sports")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSportsForAccount(@RequestHeader(value = "Authorization") String token){

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getSport_skills());
    }

    @PostMapping("/sport")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveSportForAccount(@RequestBody SportSkill sportSkill,
                                                    @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Sport sport = sportRepository.findByName(sportSkill.getSport().getName()).orElseThrow(
                () -> new UsernameNotFoundException("Sport not found exception!")
        );

        sportSkill.setApplicant(applicant);
        sportSkill.setSport(sport);
        SportSkill ss = sportSkillRepository.save(sportSkill);
        return ResponseEntity.ok().body(ss);
    }

    @DeleteMapping("/sport/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSportForAccount(@PathVariable Long id,
                                                      @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            SportSkill sportSkill = sportSkillRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Sport Skill not found whit your id!")
            );

            if (!sportSkill.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Sport Skill does not belong to the applicant.",
                        HttpStatus.BAD_REQUEST);
            }

            sportSkillRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Sport Skill deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Sport Skill.");
    }

    // Experience

    @GetMapping("/experiences")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getExperienceForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getExperiences());
    }

    @PostMapping("/experience")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveExperienceForAccount(@RequestBody Experience experience,
                                                      @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Experience exp = experience;
        exp.setApplicant(applicant);

        if(experience.getId() != null) {
            Experience e = experienceRepository.findById(experience.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Experience not found whit your id!")
            );

            if (!e.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Experience does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        Experience result = experienceRepository.save(exp);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/experience/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteExperienceToAccount(@PathVariable Long id,
                                                          @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            Experience e = experienceRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Experience not found whit your id!")
            );

            if (!e.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Experience does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            experienceRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Experience deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Experience.");
    }

    // Education

    @GetMapping("/educations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getEducationForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getEducations());
    }

    @PostMapping("/education")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveEducationForAccount(@RequestBody Education education,
                                                      @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Education educ = education;
        educ.setApplicant(applicant);

        if(education.getId() != null) {
            Education e = educationRepository.findById(education.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Education not found whit your id!")
            );

            if (!e.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Education does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        Education result = educationRepository.save(educ);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/education/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteEducationToAccount(@PathVariable Long id,
                                                       @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            Education e = educationRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Education not found whit your id!")
            );

            if (!e.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Education does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            educationRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Education deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Education.");
    }

    // Contact

    @GetMapping("/contacts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getContactsForAccount(@RequestHeader(value = "Authorization") String token){

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getContacts());
    }

    @PostMapping("/contact")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveContactForAccount(@RequestBody ContactApplicant contactApplicant,
                                                 @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        ContactType contactType = contactTypeRepository.getContactTypeByName(contactApplicant.getContact_type().getName()).orElseThrow(
                () -> new UsernameNotFoundException("Sport not found exception!")
        );

        contactApplicant.setApplicant(applicant);
        contactApplicant.setContact_type(contactType);
        ContactApplicant result = contactApplicantRepository.save(contactApplicant);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/contact/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteContactForAccount(@PathVariable Long id,
                                                   @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            ContactApplicant contact = contactApplicantRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Contact not found whit your id!")
            );

            if (!contact.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Contact does not belong to the applicant.",
                        HttpStatus.BAD_REQUEST);
            }

            contactApplicantRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Contact deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Contact.");
    }


    private Applicant getApplicantByToken(String token){
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        } else {
            new UsernameNotFoundException("User not have applicant account!");
        }

        String username = tokenProvider.getUserNameFromJwtToken(token);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username.")
        );

        Applicant applicant = applicantRepository.getApplicantByUser(user).orElseThrow(
                () -> new UsernameNotFoundException("Employer Not Found with user.")
        );

        return applicant;
    }
}
