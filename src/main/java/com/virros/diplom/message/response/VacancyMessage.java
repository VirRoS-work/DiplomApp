package com.virros.diplom.message.response;

import com.virros.diplom.model.Vacancy;

public class VacancyMessage {

    private Vacancy vacancy;
    private Long employer_id;
    private String employer_name;

    public VacancyMessage() {
    }

    public VacancyMessage(Vacancy vacancy, Long employer_id, String employer_name) {
        this.vacancy = vacancy;
        this.employer_id = employer_id;
        this.employer_name = employer_name;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Long getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(Long employer_id) {
        this.employer_id = employer_id;
    }

    public String getEmployer_name() {
        return employer_name;
    }

    public void setEmployer_name(String employer_name) {
        this.employer_name = employer_name;
    }

}
