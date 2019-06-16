package com.virros.diplom.controller;

import com.virros.diplom.controller.pdf.GeneratorPDF;
import com.virros.diplom.message.request.CompanyFilter;
import com.virros.diplom.message.request.VacancyFilter;
import com.virros.diplom.message.response.CountVacancies;
import com.virros.diplom.message.response.EmployerWithCountVacancy;
import com.virros.diplom.message.response.EventMessage;
import com.virros.diplom.message.response.VacancyMessage;
import com.virros.diplom.model.*;
import com.virros.diplom.repository.ApplicantRepository;
import com.virros.diplom.repository.EmployerRepository;
import com.virros.diplom.repository.EventRepository;
import com.virros.diplom.repository.VacancyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    EventRepository eventRepository;

    @Autowired
    GeneratorPDF generatorPDF;

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

        counts.removeIf(e -> e.getCount() <= 0);
        counts.sort((e1, e2) -> e2.getCount().compareTo(e1.getCount()));

        return ResponseEntity.ok().body(counts);

    }

    @GetMapping("/last_vacancies")
    public ResponseEntity<?> getLastTop10Vacancies() {

        List<Vacancy> vacancies = vacancyRepository.findTop10ByStatus("Активна");
        List<VacancyMessage> result = new ArrayList<>();

        vacancies.forEach(v -> result.add(new VacancyMessage(v, v.getEmployer().getId(), v.getEmployer().getName())));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/vacancies")
    public ResponseEntity<?> getVacancies() {

        List<Vacancy> vacancies = vacancyRepository.findAllByStatus("Активна");

        return ResponseEntity.ok().body(vacancies);
    }

    @PostMapping("/vacancies")
    public ResponseEntity<?> getVacanciesPageable(@RequestBody PageInfo pageInfo) {

        List<Vacancy> vacancies = vacancyRepository.findAllByStatus("Активна",
                PageRequest.of(pageInfo.getPageIndex(), pageInfo.getPageSize()));

        return ResponseEntity.ok().body(vacancies);
    }

    @GetMapping("/vacancy/{id}")
    public ResponseEntity<?> getVacancyById(@PathVariable Long id) {

        Vacancy result = vacancyRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("Vacancy not found whit your id!"));

        result.setViews(result.getViews() + 1);
        vacancyRepository.save(result);

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

    @GetMapping("/summary/{id}")
    @PreAuthorize("hasRole('COMPANY') or hasRole('ADMIN')")
    public ResponseEntity<?> getSummaryUserById(@PathVariable Long id) {

        Applicant applicant = applicantRepository.findApplicantByIdAndStatus(id, "Открытый").orElseThrow(() ->
                new UsernameNotFoundException("User not found with your id!"));

        ByteArrayOutputStream baos = generatorPDF.generatePdfToAccount(applicant);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).contentLength(baos.size()).body(baos.toByteArray());
    }

    @GetMapping("/employers")
    public ResponseEntity<?> getCountEmployers() {
        return ResponseEntity.ok().body(employerRepository.countAllByUnblockedUser());
    }

    @PostMapping("/filter/employers/count")
    public ResponseEntity<?> getCountFilterEmployers(@RequestBody CompanyFilter filter) {

        return ResponseEntity.ok().body(employerRepository.countFilterByUnblockedUser(filter.getName(),
                filter.getEmptyCompany()));
    }

    @PostMapping("/filter/employers")
    public ResponseEntity<?> getFilterEmployers(@RequestBody CompanyFilter filter) {

        List<EmployerWithCountVacancy> employers = new ArrayList<>();
        int count;

        for (Employer employer : employerRepository.findAllFilterByUnblockedUser(filter.getName(),
                filter.getEmptyCompany(),
                PageRequest.of(filter.getInfo().getPageIndex(), filter.getInfo().getPageSize()))) {

            count = (int) employer.getVacancies().stream().filter(e -> "Активна".equals(e.getStatus())).count();

            employer.setVacancies(null);
            employer.setContacts(null);
            employer.setOffices(null);

            employers.add(new EmployerWithCountVacancy(employer, count));
        }

        return ResponseEntity.ok().body(employers);
    }

    @PostMapping("/filter/vacancies/count")
    public ResponseEntity<?> getCountFilterVacancies(@RequestBody VacancyFilter filter) {

        return ResponseEntity.ok().body(vacancyRepository.countFilterVacancies(filter.getKey(),
                filter.getGlobal()));
    }

    @PostMapping("/filter/vacancies")
    public ResponseEntity<?> getFilterVacancies(@RequestBody VacancyFilter filter) {

        System.out.println(filter.toString());

        List<VacancyMessage> result = new ArrayList<>();

        vacancyRepository.findAllFilterVacancies(filter.getKey(),
                filter.getGlobal(),
                PageRequest.of(filter.getInfo().getPageIndex(), filter.getInfo().getPageSize()))
                .forEach( v -> result.add(new VacancyMessage(v, v.getEmployer().getId(), v.getEmployer().getName())));

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/employers")
    public ResponseEntity<?> getListEmployers(@RequestBody PageInfo pageInfo) {

        List<EmployerWithCountVacancy> employers = new ArrayList<>();
        int count;

        for (Employer employer : employerRepository.findByUnblockedUser(
                PageRequest.of(pageInfo.getPageIndex(), pageInfo.getPageSize()))) {

            count = 0;
            for (Vacancy vacancy : employer.getVacancies()) if ("Активна".equals(vacancy.getStatus())) count++;
            employer.setVacancies(null);
            employer.setContacts(null);
            employer.setOffices(null);

            employers.add(new EmployerWithCountVacancy(employer, count));
        }

        return ResponseEntity.ok().body(employers);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id){

        Event event = eventRepository.findById(id).orElseThrow(() ->
          new UsernameNotFoundException("Event not found with your id!"));

        EventMessage result  = new EventMessage(event, event.getEmployer().getId(), event.getEmployer().getName());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/events/nearest")
    public ResponseEntity<?> getNearestEvents(){

        List<Event> events = eventRepository.findTop5ByTimeAfterOrderByTime(LocalDateTime.now());
        List<EventMessage> result = new ArrayList<>();

        if(!events.isEmpty()) {
            events.forEach(event -> {

                Employer employer = new Employer();
                employer.setId(event.getEmployer().getId());
                employer.setName(event.getEmployer().getName());

                result.add(new EventMessage(event, event.getEmployer().getId(), event.getEmployer().getName()));

            });
        }

        return ResponseEntity.ok().body(result);
    }




}
