package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "language_skills")
public class LanguageSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "language_id")
    private Language language;

    @NotBlank
    private String ownership_level;

    public LanguageSkill() {
    }

    public LanguageSkill(Applicant applicant, Language language, String ownership_level) {
        this.applicant = applicant;
        this.language = language;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getOwnership_level() {
        return ownership_level;
    }

    public void setOwnership_level(String ownership_level) {
        this.ownership_level = ownership_level;
    }

    @Override
    public String toString() {
        return "LanguageSkill{" +
                "id=" + id +
                ", language=" + language +
                ", ownership_level='" + ownership_level + '\'' +
                '}';
    }
}
