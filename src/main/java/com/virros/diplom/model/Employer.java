package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "employers")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    private String type;

    @NotBlank
    private String number_of_person;

    @Size(max = 250)
    private String address;
    @Size(max = 250)
    private String site;
    @Size(max = 2000)
    private String description;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "employer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<ContactPerson> contacts;

    @OneToMany(mappedBy = "employer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Office> offices;

    @OneToMany(mappedBy = "employer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Vacancy> vacancies;

    @OneToMany(mappedBy = "employer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Event> events;

    public Employer() {
    }

    public Employer(String name, String type, String number_of_person, String address, String site, String description,
                    User user, Set<ContactPerson> contacts, Set<Office> offices, Set<Vacancy> vacancies, Set<Event> events) {
        this.name = name;
        this.type = type;
        this.number_of_person = number_of_person;
        this.address = address;
        this.site = site;
        this.description = description;
        this.user = user;
        this.contacts = contacts;
        this.offices = offices;
        this.vacancies = vacancies;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber_of_person() {
        return number_of_person;
    }

    public void setNumber_of_person(String number_of_person) {
        this.number_of_person = number_of_person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ContactPerson> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactPerson> contacts) {
        this.contacts = contacts;
    }

    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
