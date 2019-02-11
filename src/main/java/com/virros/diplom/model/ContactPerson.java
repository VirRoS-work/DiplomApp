package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "contact_persons")
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    private String father_name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employer_id")
    @JsonIgnore
    private Employer employer;

    @OneToMany(mappedBy = "contact_person",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<ContactPersonContact> contacts;

    public ContactPerson() {
    }

    public ContactPerson(String first_name, String last_name, String father_name, Employer employer, Set<ContactPersonContact> contacts) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.father_name = father_name;
        this.employer = employer;
        this.contacts = contacts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Set<ContactPersonContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactPersonContact> contacts) {
        this.contacts = contacts;
    }

}
