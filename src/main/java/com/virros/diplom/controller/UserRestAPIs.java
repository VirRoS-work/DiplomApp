package com.virros.diplom.controller;

import com.virros.diplom.controller.pdf.GeneratorPDF;
import com.virros.diplom.model.*;
import com.virros.diplom.repository.*;
import com.virros.diplom.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    SpecializationApplicantRepository specializationApplicantRepository;

    @Autowired
    FieldOfActivityRepository fieldOfActivityRepository;

    @Autowired
    ApplicantInfoRepository applicantInfoRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    JwtProvider tokenProvider;

    @Autowired
    GeneratorPDF generatorPDF;

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getApplicantForAccount(@RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok().body(getApplicantByToken(token));
    }

    @PostMapping("/info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveApplicantForAccount(@RequestBody Applicant applicant,
                                                     @RequestHeader(value = "Authorization") String token) {

        Applicant app = getApplicantByToken(token);

        app.setFirst_name(applicant.getFirst_name());
        app.setLast_name(applicant.getLast_name());
        app.setFather_name(applicant.getFather_name());
        app.setSex(applicant.isSex());
        app.setDate_of_birth(applicant.getDate_of_birth());

        Applicant result = applicantRepository.save(app);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> switchStatusToAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);
        applicant.setStatus(applicant.getStatus().equals("Закрытый") ? "Открытый" : "Закрытый");

        Applicant result = applicantRepository.save(applicant);
        return ResponseEntity.ok().body(result);
    }


    // Languages

    @GetMapping("/languages")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getLanguagesForAccount(@RequestHeader(value = "Authorization") String token) {

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
    public ResponseEntity<?> getSportsForAccount(@RequestHeader(value = "Authorization") String token) {

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

    // Books

    @GetMapping("/books")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getBooksForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getBooks());
    }

    @PostMapping("/book")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveBookForAccount(@RequestBody Book book,
                                                 @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        book.setApplicant(applicant);
        Book result = bookRepository.save(book);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/book/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBookForAccount(@PathVariable Long id,
                                                  @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null){
            Book book = bookRepository.findById(id).orElseThrow(() ->
            new UsernameNotFoundException("Book not found whit your id"));

            if (!book.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Book does not belong to the applicant.",
                        HttpStatus.BAD_REQUEST);
            }

            bookRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Book deleted");
        }

        return ResponseEntity.badRequest().body(">>> Not Book");
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

        if (experience.getId() != null) {
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

        if (education.getId() != null) {
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
    public ResponseEntity<?> getContactsForAccount(@RequestHeader(value = "Authorization") String token) {

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

    // Specialization

    @GetMapping("/specializations")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSpecializationsForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getSpecializations());
    }

    @PostMapping("/specialization")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveSpecializationForAccount(@RequestBody SpecializationApplicant specializationApplicant,
                                                          @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        FieldOfActivity field = fieldOfActivityRepository.findById(specializationApplicant.getField_of_activity().getId()).orElseThrow(
                () -> new UsernameNotFoundException("Field of activity not found exception!")
        );

        SpecializationApplicant specialization = new SpecializationApplicant();

        specialization.setApplicant(applicant);
        specialization.setField_of_activity(field);
        specialization.setSpecialization(specializationApplicant.getSpecialization());

        SpecializationApplicant result = specializationApplicantRepository.save(specialization);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/specialization/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSpecializationForAccount(@PathVariable Long id,
                                                            @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            SpecializationApplicant sa = specializationApplicantRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Specialization not found whit your id!")
            );

            if (!sa.getApplicant().getId().equals(applicant.getId())) {
                return new ResponseEntity<String>("Fail -> This Specialization does not belong to the applicant.",
                        HttpStatus.BAD_REQUEST);
            }

            specializationApplicantRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Specialization deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Specialization.");
    }

    // ApplicantInfo

    @GetMapping("/information")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getInformationForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        return ResponseEntity.ok().body(applicant.getInfo());
    }

    @PostMapping("/information")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveInformationForAccount(@RequestBody ApplicantInfo applicantInfo,
                                                       @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        ApplicantInfo info = applicantInfoRepository.findByApplicant(applicant).orElseThrow(
                () -> new UsernameNotFoundException("Information not found exception!")
        );

        info.setReady_to_move(applicantInfo.isReady_to_move());
        info.setReady_for_remove_work(applicantInfo.isReady_for_remove_work());
        info.setCity(applicantInfo.getCity().trim().equals("") ? null : applicantInfo.getCity().trim());
        info.setCitizenship(applicantInfo.getCitizenship().trim().equals("") ? null : applicantInfo.getCitizenship().trim());
        info.setFamily_status(applicantInfo.getFamily_status());
        info.setChildren(applicantInfo.isChildren());
        info.setAbout_me(applicantInfo.getAbout_me().trim().equals("") ? null : applicantInfo.getAbout_me().trim());
        info.setHobby(applicantInfo.getHobby().trim().equals("") ? null : applicantInfo.getHobby().trim());
        info.setSalary(applicantInfo.getSalary());
        info.setAcademic_degree(applicantInfo.getAcademic_degree().trim().equals("") ? null : applicantInfo.getAcademic_degree().trim());

        ApplicantInfo result = applicantInfoRepository.save(info);

        return ResponseEntity.ok().body(result);
    }

    // Summary
    @GetMapping("/summary")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSummaryForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);
        ByteArrayOutputStream baos = generatorPDF.generatePdfToAccount(applicant);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).contentLength(baos.size()).body(baos.toByteArray());
    }

    // Bookmarks
    @GetMapping("/bookmarks")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getBookmarksForAccount(@RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);
        List<Bookmark> bookmarks = bookmarkRepository.findAllByApplicant(applicant);

        List<Vacancy> vacancies = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getVacancy().getStatus().equals("Активна")) vacancies.add(bookmark.getVacancy());
        }

        return ResponseEntity.ok().body(vacancies);
    }

    @PostMapping("/bookmark")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveBookmarksForAccount(@RequestBody Long vacancy_id,
                                                     @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Vacancy vacancy = vacancyRepository.findById(vacancy_id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found with your id!"));

        Bookmark bookmark = new Bookmark(applicant, vacancy);
        Bookmark result = bookmarkRepository.save(bookmark);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/bookmark/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteBookmarkForAccount(@PathVariable Long id,
                                                      @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        if (id != null) {
            Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Vacancy not found whit your id!")
            );

            Bookmark bookmark = bookmarkRepository.findByApplicantAndVacancy(applicant, vacancy).orElseThrow(() ->
                    new UsernameNotFoundException("Bookmark not found whit your id!"));

            bookmarkRepository.delete(bookmark);
            return ResponseEntity.ok().body(">>> Bookmark deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Bookmark.");
    }

    @GetMapping("/bookmark/check/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> checkBookmark(@PathVariable Long id,
                                           @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found with your id!"));

        Boolean result = bookmarkRepository.existsByApplicantAndVacancy(applicant, vacancy);

        return ResponseEntity.ok().body(result);
    }

    // Notifications

    @PostMapping("/notification")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> saveNotificationForAccount(@RequestBody Long vacancy_id,
                                                        @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Vacancy vacancy = vacancyRepository.findById(vacancy_id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found with your id!"));

        Notification notification = new Notification(applicant, vacancy, LocalDateTime.now(), "Новый");
        Notification result = notificationRepository.save(notification);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/notification/check/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> checkNotificationForApplicant(@PathVariable Long id,
                                                           @RequestHeader(value = "Authorization") String token) {

        Applicant applicant = getApplicantByToken(token);

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found with your id!"));

        Boolean result = notificationRepository.existsByApplicantAndVacancy(applicant, vacancy);

        return ResponseEntity.ok().body(result);
    }



    private Applicant getApplicantByToken(String token) {
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
