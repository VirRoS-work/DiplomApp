package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "educations")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @NotBlank
    private String educational_institution;

    @NotBlank
    private String faculty;

    @NotBlank
    private String specialization;

    private LocalDate date_start;

    private LocalDate date_end;

    private String level_education;

    private String form_training;

    public Education() {
    }

    public Education(Applicant applicant, String educational_institution, String faculty, String specialization,
                     LocalDate date_start, LocalDate date_end, String level_education, String form_training) {
        this.applicant = applicant;
        this.educational_institution = educational_institution;
        this.faculty = faculty;
        this.specialization = specialization;
        this.date_start = date_start;
        this.date_end = date_end;
        this.level_education = level_education;
        this.form_training = form_training;
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

    public String getEducational_institution() {
        return educational_institution;
    }

    public void setEducational_institution(String educational_institution) {
        this.educational_institution = educational_institution;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDate date_start) {
        this.date_start = date_start;
    }

    public LocalDate getDate_end() {
        return date_end;
    }

    public void setDate_end(LocalDate date_end) {
        this.date_end = date_end;
    }

    public String getLevel_education() {
        return level_education;
    }

    public void setLevel_education(String level_education) {
        this.level_education = level_education;
    }

    public String getForm_training() {
        return form_training;
    }

    public void setForm_training(String form_training) {
        this.form_training = form_training;
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", educational_institution='" + educational_institution + '\'' +
                ", faculty='" + faculty + '\'' +
                ", specialization='" + specialization + '\'' +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                ", level_education='" + level_education + '\'' +
                ", form_training='" + form_training + '\'' +
                '}';
    }
}
