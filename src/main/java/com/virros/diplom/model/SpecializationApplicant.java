package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "specializations_applicant")
public class SpecializationApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "field_of_activity_id")
    private FieldOfActivity field_of_activity;

    @NotBlank
    private String specialization;

    public SpecializationApplicant() {
    }

    public SpecializationApplicant(Applicant applicant, FieldOfActivity field_of_activity, String specialization) {
        this.applicant = applicant;
        this.field_of_activity = field_of_activity;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public FieldOfActivity getField_of_activity() {
        return field_of_activity;
    }

    public void setField_of_activity(FieldOfActivity field_of_activity) {
        this.field_of_activity = field_of_activity;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "SpecializationApplicant{" +
                "id=" + id +
                ", field_of_activity=" + field_of_activity +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
