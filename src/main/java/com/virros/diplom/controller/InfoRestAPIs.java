package com.virros.diplom.controller;

import com.virros.diplom.model.Constants;
import com.virros.diplom.repository.ContactTypeRepository;
import com.virros.diplom.repository.FieldOfActivityRepository;
import com.virros.diplom.repository.LanguageRepository;
import com.virros.diplom.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/info")
public class InfoRestAPIs {

    @Autowired
    Constants constants;

    @Autowired
    ContactTypeRepository contactTypeRepository;

    @Autowired
    FieldOfActivityRepository fieldOfActivityRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    SportRepository sportRepository;

    @GetMapping("/companytypes")
    public ResponseEntity<?> getInfoAboutTypesCompany(){

        return ResponseEntity.ok().body(constants.getTypesConpany());
    }

    @GetMapping("/companycounts")
    public ResponseEntity<?> getInfoAboutCountsCompany(){

        return ResponseEntity.ok().body(constants.getCountEmployeesConpany());
    }

    @GetMapping("/contacttypes")
    public ResponseEntity<?> getInfoAboutContactTypes() {

        return ResponseEntity.ok().body(contactTypeRepository.findAll());
    }

    @GetMapping("/fieldsofactivities")
    public ResponseEntity<?> getInfoAboutFieldsOfActivities() {
        return ResponseEntity.ok().body(fieldOfActivityRepository.findAll());
    }

    @GetMapping("/employmenttypes")
    public ResponseEntity<?> getInfoAboutEmploymentTypes() {
        return ResponseEntity.ok().body(constants.getTypesEmployment());
    }

    @GetMapping("/languages")
    public ResponseEntity<?> getInfoAboutLanguages() {
        return ResponseEntity.ok().body(languageRepository.findAll());
    }

    @GetMapping("/sports")
    public ResponseEntity<?> getInfoAboutSports() {
        return ResponseEntity.ok().body(sportRepository.findAll());
    }

    @GetMapping("/formstraining")
    public ResponseEntity<?> getInfoAboutFormsTraining() {
        return ResponseEntity.ok().body(constants.getFormTraining());
    }

    @GetMapping("/familystatuses")
    public ResponseEntity<?> getInfoAboutFamilyStatuses() {
        return ResponseEntity.ok().body(constants.getFamilyStatus());
    }

}
