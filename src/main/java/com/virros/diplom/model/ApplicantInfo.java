package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "applicants_info")
public class ApplicantInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    private Boolean ready_to_move;

    private Boolean ready_for_remove_work;

    private String city;

    private String citizenship;

    private String family_status;

    private Boolean children;

    private String about_me;

    private String hobby;

    private Long salary;

    private String academic_degree;

    public ApplicantInfo() {
    }

    public ApplicantInfo(Applicant applicant) {
        this.applicant = applicant;
    }

    public ApplicantInfo(Applicant applicant, Boolean ready_to_move, Boolean ready_for_remove_work, String city,
                         String citizenship, String family_status, Boolean children, String about_me, String hobby,
                         Long salary, String academic_degree) {
        this.applicant = applicant;
        this.ready_to_move = ready_to_move;
        this.ready_for_remove_work = ready_for_remove_work;
        this.city = city;
        this.citizenship = citizenship;
        this.family_status = family_status;
        this.children = children;
        this.about_me = about_me;
        this.hobby = hobby;
        this.salary = salary;
        this.academic_degree = academic_degree;
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

    public Boolean isReady_to_move() {
        return ready_to_move;
    }

    public void setReady_to_move(Boolean ready_to_move) {
        this.ready_to_move = ready_to_move;
    }

    public Boolean isReady_for_remove_work() {
        return ready_for_remove_work;
    }

    public void setReady_for_remove_work(Boolean ready_for_remove_work) {
        this.ready_for_remove_work = ready_for_remove_work;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getFamily_status() {
        return family_status;
    }

    public void setFamily_status(String family_status) {
        this.family_status = family_status;
    }

    public Boolean isChildren() {
        return children;
    }

    public void setChildren(Boolean children) {
        this.children = children;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getAcademic_degree() {
        return academic_degree;
    }

    public void setAcademic_degree(String academic_degree) {
        this.academic_degree = academic_degree;
    }

    @Override
    public String toString() {
        return "ApplicantInfo{" +
                "id=" + id +
                ", ready_to_move=" + ready_to_move +
                ", ready_for_remove_work=" + ready_for_remove_work +
                ", city='" + city + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", family_status='" + family_status + '\'' +
                ", children=" + children +
                ", about_me='" + about_me + '\'' +
                ", hobby='" + hobby + '\'' +
                ", salary=" + salary +
                ", academic_degree='" + academic_degree + '\'' +
                '}';
    }
}
