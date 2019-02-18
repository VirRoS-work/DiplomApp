package com.virros.diplom.message.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class SignUpFormApplicant {

    private SignUpForm signUpForm;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    private String father_name;

    private Boolean sex;

    private LocalDate date_of_birth;

    public SignUpForm getSignUpForm() {
        return signUpForm;
    }

    public void setSignUpForm(SignUpForm signUpForm) {
        this.signUpForm = signUpForm;
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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "SignUpFormApplicant{" +
                "signUpForm=" + signUpForm +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", father_name='" + father_name + '\'' +
                ", sex=" + sex +
                ", date_of_birth=" + date_of_birth +
                '}';
    }
}
