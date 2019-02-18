package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @NotBlank
    private String company_name;

    @NotBlank
    private String position;

    private LocalDate date_start;

    private LocalDate date_end;

    private String duties;

    private String achievements;

    public Experience() {
    }

    public Experience(Applicant applicant, String company_name, String position, LocalDate date_start,
                      LocalDate date_end, String duties, String achievements) {
        this.applicant = applicant;
        this.company_name = company_name;
        this.position = position;
        this.date_start = date_start;
        this.date_end = date_end;
        this.duties = duties;
        this.achievements = achievements;
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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String compsny_name) {
        this.company_name = compsny_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", company_name='" + company_name + '\'' +
                ", position='" + position + '\'' +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                ", duties='" + duties + '\'' +
                ", achievements='" + achievements + '\'' +
                '}';
    }
}
