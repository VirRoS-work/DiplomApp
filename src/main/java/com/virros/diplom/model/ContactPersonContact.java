package com.virros.diplom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "contact_person_contacts")
public class ContactPersonContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_person_id")
    @JsonIgnore
    private ContactPerson contact_person;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "contact_type_id")
    private ContactType contact_type;

    @NotBlank
    private String value;

    public ContactPersonContact() {
    }

    public ContactPersonContact(ContactPerson contact_person, ContactType contact_type, String value) {
        this.contact_person = contact_person;
        this.contact_type = contact_type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactPerson getContact_person() {
        return contact_person;
    }

    public void setContact_person(ContactPerson contact_person) {
        this.contact_person = contact_person;
    }

    public ContactType getContact_type() {
        return contact_type;
    }

    public void setContact_type(ContactType contact_type) {
        this.contact_type = contact_type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ContactPersonContact{" +
                "id=" + id +
                ", contact_person=" + contact_person +
                ", contact_type=" + contact_type +
                ", value='" + value + '\'' +
                '}';
    }
}
