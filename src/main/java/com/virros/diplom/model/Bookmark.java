package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    @JsonIgnore
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    public Bookmark() {
    }

    public Bookmark(Applicant applicant, Vacancy vacancy) {
        this.applicant = applicant;
        this.vacancy = vacancy;
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

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", applicant=" + applicant +
                ", vacancy=" + vacancy +
                '}';
    }
}
