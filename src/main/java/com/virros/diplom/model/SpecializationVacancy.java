package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "specializations_vacancies")
public class SpecializationVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "field_of_activity_id")
    private FieldOfActivity field_of_activity;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vacancy_id")
    @JsonIgnore
    private Vacancy vacancy;

    @NotBlank
    private String specialization;

    public SpecializationVacancy() {
    }

    public SpecializationVacancy(FieldOfActivity field_of_activity, Vacancy vacancy, String specialization) {
        this.field_of_activity = field_of_activity;
        this.vacancy = vacancy;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FieldOfActivity getField_of_activity() {
        return field_of_activity;
    }

    public void setField_of_activity(FieldOfActivity field_of_activity) {
        this.field_of_activity = field_of_activity;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
