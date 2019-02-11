package com.virros.diplom.controller;

import com.google.gson.Gson;
import com.virros.diplom.model.Constants;
import com.virros.diplom.repository.ContactTypeRepository;
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
    Gson gson;

    @GetMapping("/companytypes")
    public ResponseEntity<?> getInfoAboutTypesCompany(){

        return ResponseEntity.ok().body(constants.getTypesConpany());
    }

    @GetMapping("/companycounts")
    public ResponseEntity<?> getInfoAboutCountsCompany(){

        return ResponseEntity.ok().body(constants.getCountEmployeesConpany());
    }

    @GetMapping("/contacttypes")
    public ResponseEntity<?> detInfoAboutContactTypes() {

        return ResponseEntity.ok().body(contactTypeRepository.findAll());
    }



}
