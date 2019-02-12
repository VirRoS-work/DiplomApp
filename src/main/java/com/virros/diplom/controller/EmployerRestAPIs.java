package com.virros.diplom.controller;

import com.virros.diplom.model.*;
import com.virros.diplom.repository.*;
import com.virros.diplom.security.jwt.JwtAuthEntryPoint;
import com.virros.diplom.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employer")
public class EmployerRestAPIs {

    private static final Logger logger = LoggerFactory.getLogger(EmployerRestAPIs.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    ContactPersonRepository contactPersonRepository;

    @Autowired
    ContactPersonContactRepository contactPersonContactRepository;

    @Autowired
    ContactTypeRepository contactTypeRepository;

    @Autowired
    OfficeRepository officeRepository;

    @Autowired
    SpecializationVacancyRepository specializationVacancyRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    FieldOfActivityRepository fieldOfActivityRepository;

    @Autowired
    private JwtProvider tokenProvider;

    @GetMapping("/info")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getEmployerForAccount(@RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok().body(getEmployerByToken(token));
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> editEmployerForAccount(@RequestBody Employer employer,
                                                    @RequestHeader(value = "Authorization") String token) {

        Employer empl = getEmployerByToken(token);

        employer.setId(empl.getId());
        employer.setUser(empl.getUser());
        employerRepository.save(employer);

        return ResponseEntity.ok().body(employer);
    }

    //Contact Person methods

    @GetMapping("/contacts")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getContactPersonsForAccount(@RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok().body(getEmployerByToken(token).getContacts());
    }

    @PostMapping("/contact")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> saveContactPersonToAccount(@RequestBody ContactPerson person,
                                                        @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        ContactPerson contactPerson = person;
        contactPerson.setEmployer(employer);

        if (person.getId() != null) {
            ContactPerson cont = contactPersonRepository.findById(person.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Contact Person not found whit your id!")
            );

            if (!cont.getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Contact Person does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        contactPersonRepository.save(contactPerson);
        return ResponseEntity.ok().body(contactPerson);
    }

    @DeleteMapping("/contact/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteContactPersonToAccount(@PathVariable Long id,
                                                          @RequestHeader(value = "Authorization") String token) {
        Employer employer = getEmployerByToken(token);

        if (id != null) {
            ContactPerson cont = contactPersonRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Contact Person not found whit your id!")
            );

            if (!cont.getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Contact Person does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            contactPersonRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Contact Person deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Contact Person.");
    }

    //Contact methods

    @DeleteMapping("/contactperson/contact/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteContactToContactPerson(@PathVariable Long id,
                                                          @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        if (id != null) {
            ContactPersonContact contact = contactPersonContactRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Contact not found whit your id!")
            );

            if (!contact.getContact_person().getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Contact does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            contactPersonContactRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Contact deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Contact.");
    }

    @PostMapping("/contactperson/contact")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> addContactToContactPersonToAccount(@RequestBody ContactPersonContact contact,
                                                                @RequestHeader(value = "Authorization") String token) {

        ContactPerson contactPerson = contactPersonRepository.findById(contact.getId()).orElseThrow(() ->
                new UsernameNotFoundException("Contact Person not found!")
        );

        ContactType contactType = contactTypeRepository.findById(contact.getContact_type().getId()).orElseThrow(() ->
                new UsernameNotFoundException("Contact Type not found!")
        );

        Employer employer = getEmployerByToken(token);

        if(!contactPerson.getEmployer().getId().equals(employer.getId())){
            return new ResponseEntity<String>("Fail -> This Contact Person does not belong to the company.",
                    HttpStatus.BAD_REQUEST);
        }

        contact.setContact_person(contactPerson);
        contact.setContact_type(contactType);
        contact.setId(null);

        logger.info(contact.toString());

        contactPersonContactRepository.save(contact);

        return ResponseEntity.ok().body(contact);

    }

    //Office methods

    @GetMapping("/offices")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getOfficesForAccount(@RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok().body(getEmployerByToken(token).getOffices());
    }

    @PostMapping("/office")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> saveOfficeForAccount(@RequestBody Office office,
                                                  @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        Office off = office;
        off.setEmployer(employer);

        if (office.getId() != null) {
            Office o = officeRepository.findById(office.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Office not found whit your id!")
            );

            if (!o.getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Office does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        Office result = officeRepository.save(off);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/office/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteOfficeToAccount(@PathVariable Long id,
                                                   @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        if (id != null) {
            Office office = officeRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Office not found whit your id!")
            );

            if (!office.getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Office does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            officeRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Office deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Office.");
    }

    //Vacancy methods

    @GetMapping("/vacancies")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getVacanciesForAccount(@RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok().body(getEmployerByToken(token).getVacancies());
    }


    @PostMapping("/vacancy")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> saveVacancyToAccount(@RequestBody Vacancy vacancy,
                                                  @RequestHeader(value = "Authorization") String token) {
        Employer employer = getEmployerByToken(token);

        Vacancy vac = vacancy;
        vac.setEmployer(employer);

        if (vacancy.getId() != null) {
            Vacancy o = vacancyRepository.findById(vacancy.getId()).orElseThrow(() ->
                    new UsernameNotFoundException("Vacancy not found whit your id!")
            );

            if (!o.getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Vacancy does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        Vacancy result = vacancyRepository.save(vac);

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/vacancy/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteVacancyFronAccount(@PathVariable Long id,
                                                           @RequestHeader(value = "Authorization") String token) {
        Employer employer = getEmployerByToken(token);

        if (id != null) {
            Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("Vacancy not found whit your id!")
            );

            if (!vacancy.getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Vacancy does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            vacancyRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Vacancy deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Vacancy.");
    }

    @PostMapping("/vacancy/specialization")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> saveSpecializationToVacancy(@RequestBody SpecializationVacancy specialization,
                                                         @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        if (specialization.getId() != null) {
            Vacancy vacancy = vacancyRepository.findById(specialization.getId()).orElseThrow(() ->
            new UsernameNotFoundException("Vacancy not found exception with your id"));

            if(!vacancy.getEmployer().getId().equals(employer.getId())){
                return new ResponseEntity<String>("Fail -> This Vacancy does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            specialization.setVacancy(vacancy);
            specialization.setId(null);
            SpecializationVacancy specializationVacancy = specializationVacancyRepository.save(specialization);
            return ResponseEntity.ok().body(specializationVacancy);
        }

        return ResponseEntity.badRequest().body(">>> Not Vacancy.");
    }

    @DeleteMapping("/vacancy/specialization/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSpecializationToVacancy(@PathVariable Long id,
                                                           @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        if(id != null) {
            SpecializationVacancy specialization = specializationVacancyRepository.findById(id).orElseThrow(() ->
            new UsernameNotFoundException("Specialization not found whit your id!"));

            if (!specialization.getVacancy().getEmployer().getId().equals(employer.getId())) {
                return new ResponseEntity<String>("Fail -> This Specialization does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            specializationVacancyRepository.deleteById(id);
            return ResponseEntity.ok().body(">>> Specialization deleted.");
        }

        return ResponseEntity.badRequest().body(">>> Not Specialization.");
    }

    @PutMapping("/vacancy/status")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> editStatusVacancy(@RequestBody Long id,
                                               @RequestHeader(value = "Authorization") String token) {

        Employer employer = getEmployerByToken(token);

        if(id != null) {
            Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() ->
            new UsernameNotFoundException("Vacancy not fount with your id!"));

            if(!vacancy.getEmployer().getId().equals(employer.getId())){
                return new ResponseEntity<String>("Fail -> This Vacancy does not belong to the company.",
                        HttpStatus.BAD_REQUEST);
            }

            vacancy.setStatus("Активна".equals(vacancy.getStatus()) ? "Неактивна" : "Активна");
            vacancyRepository.save(vacancy);

            return ResponseEntity.ok().body(vacancy);
        }

        return ResponseEntity.badRequest().body(">>> Not Vacancy.");
    }

    private Employer getEmployerByToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        } else {
            new UsernameNotFoundException("User not have company account!");
        }

        String username = tokenProvider.getUserNameFromJwtToken(token);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username.")
        );

        Employer employer = employerRepository.getEmployerByUser(user).orElseThrow(
                () -> new UsernameNotFoundException("Employer Not Found with user.")
        );

        return employer;
    }

}