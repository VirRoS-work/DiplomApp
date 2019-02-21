package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String status;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    private String father_name;

    private boolean sex;

    private LocalDate date_of_birth;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private ApplicantInfo info;

    @OneToMany(mappedBy = "applicant",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<LanguageSkill> language_skills;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<SportSkill> sport_skills;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Experience> experiences;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<Education> educations;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<ContactApplicant> contacts;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<SpecializationApplicant> specializations;

    public Applicant() {
    }

    public Applicant(String status, String first_name, String last_name, String father_name, boolean sex,
                     LocalDate date_of_birth, User user) {
        this.status = status;
        this.first_name = first_name;
        this.last_name = last_name;
        this.father_name = father_name;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.user = user;
    }

    public Applicant(String status, String first_name, String last_name, String father_name, boolean sex,
                     LocalDate date_of_birth, User user, Set<ContactApplicant> contacts, Set<LanguageSkill> languageSkills,
                     Set<SportSkill> sportSkills, Set<Experience> experiences, Set<Education> educations,
                     Set<SpecializationApplicant> specializations, ApplicantInfo applicantInfo) {
        this.status = status;
        this.first_name = first_name;
        this.last_name = last_name;
        this.father_name = father_name;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.user = user;
        this.contacts = contacts;
        this.language_skills = languageSkills;
        this.sport_skills = sportSkills;
        this.experiences = experiences;
        this.educations = educations;
        this.specializations = specializations;
        this.info = applicantInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<LanguageSkill> getLanguage_skills() {
        return language_skills;
    }

    public void setLanguage_skills(Set<LanguageSkill> language_skills) {
        this.language_skills = language_skills;
    }

    public Set<SportSkill> getSport_skills() {
        return sport_skills;
    }

    public void setSport_skills(Set<SportSkill> sport_skills) {
        this.sport_skills = sport_skills;
    }

    public Set<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        this.experiences = experiences;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    public Set<ContactApplicant> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactApplicant> contacts) {
        this.contacts = contacts;
    }

    public Set<SpecializationApplicant> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<SpecializationApplicant> specializations) {
        this.specializations = specializations;
    }

    public ApplicantInfo getInfo() {
        return info;
    }

    public void setInfo(ApplicantInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", father_name='" + father_name + '\'' +
                ", sex=" + sex +
                ", date_of_birth=" + date_of_birth +
                ", contacts=" + contacts +
                ", language_skills=" + language_skills +
                ", sport_skills=" + sport_skills +
                ", experiences=" + experiences +
                ", educations=" + educations +
                ", specializations=" + specializations +
                ", info=" + info +
                '}';
    }
}
