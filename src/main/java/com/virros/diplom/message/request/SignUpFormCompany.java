package com.virros.diplom.message.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpFormCompany {

    private SignUpForm signUpForm;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    private String type;

    @NotBlank
    private String count;

    private String address;

    private String site;

    private String description;

    public SignUpForm getSignUpForm() {
        return signUpForm;
    }

    public void setSignUpForm(SignUpForm signUpForm) {
        this.signUpForm = signUpForm;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    @Override
    public String toString() {
        return "SignUpFormCompany{" +
                "signUpForm=" + signUpForm +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", count='" + count + '\'' +
                ", address='" + address + '\'' +
                ", site='" + site + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
