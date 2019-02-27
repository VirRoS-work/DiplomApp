package com.virros.diplom.controller;

import com.virros.diplom.message.response.CountVacancies;
import com.virros.diplom.model.Applicant;
import com.virros.diplom.model.Employer;
import com.virros.diplom.model.Vacancy;
import com.virros.diplom.repository.ApplicantRepository;
import com.virros.diplom.repository.EmployerRepository;
import com.virros.diplom.repository.VacancyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/content")
public class ContentRestAPIs {

    private static final Logger logger = LoggerFactory.getLogger(EmployerRestAPIs.class);

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    ApplicantRepository applicantRepository;

    @GetMapping("/count_vacancies")
    public ResponseEntity<?> getCompaniesVacaniesCount() {

        List<Employer> employers = employerRepository.findAll();
        List<CountVacancies> counts = new ArrayList<>();

        for (Employer obj : employers) {

            int count = 0;

            for (Vacancy vacancy : obj.getVacancies()) {
                if ("Активна".equals(vacancy.getStatus())) count++;
            }

            CountVacancies countVacancies = new CountVacancies(obj.getId(), obj.getName(), count);
            counts.add(countVacancies);
        }

        counts.sort((e1, e2) -> e2.getCount().compareTo(e1.getCount()));

        return ResponseEntity.ok().body(counts);

    }

    @GetMapping("/last_vacancies")
    public ResponseEntity<?> getLastTop10Vacancies() {

        List<Vacancy> vacancies = vacancyRepository.findTop10ByStatus("Активна");

        return ResponseEntity.ok().body(vacancies);
    }

    @GetMapping("/vacancy/{id}")
    public ResponseEntity<?> getVacancyById(@PathVariable Long id) {

        Vacancy result = vacancyRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found whit your id!"));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/company/vacancy/{id}")
    public ResponseEntity<?> getCompanyByVacancyId(@PathVariable Long id) {

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found whit your id!"));

        Employer result = vacancy.getEmployer();
        result.setVacancies(null);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {

        Employer result = employerRepository.findById(id).orElseThrow(() ->
        new UsernameNotFoundException("Company not found with your id"));

        result.getVacancies().removeIf(n -> (!n.getStatus().equals("Активна")));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/applicants")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(applicantRepository.findAllByStatus("Открытый"));
    }

    @GetMapping("/applicant/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        Applicant result = applicantRepository.findApplicantByIdAndStatus(id, "Открытый").orElseThrow(() ->
        new UsernameNotFoundException("User not found with your id!"));

        return ResponseEntity.ok().body(result);
    }

}
