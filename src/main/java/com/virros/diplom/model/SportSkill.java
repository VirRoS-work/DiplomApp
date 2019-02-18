package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "sport_skills")
public class SportSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sport_id")
    private Sport sport;

    @NotBlank
    private String ownership_level;

    public SportSkill() {
    }

    public SportSkill(Applicant applicant, Sport sport, @NotBlank String ownership_level) {
        this.applicant = applicant;
        this.sport = sport;
        this.ownership_level = ownership_level;
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public String getOwnership_level() {
        return ownership_level;
    }

    public void setOwnership_level(String ownership_level) {
        this.ownership_level = ownership_level;
    }

    @Override
    public String toString() {
        return "SportSkill{" +
                "id='" + id + '\'' +
                ", sport=" + sport +
                ", ownership_level='" + ownership_level + '\'' +
                '}';
    }
}
