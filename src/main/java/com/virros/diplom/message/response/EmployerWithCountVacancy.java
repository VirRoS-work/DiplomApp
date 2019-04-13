package com.virros.diplom.message.response;

import com.virros.diplom.model.Employer;

public class EmployerWithCountVacancy {

    private Employer employer;
    private Integer count;

    public EmployerWithCountVacancy() {
    }

    public EmployerWithCountVacancy(Employer employer, Integer count) {
        this.employer = employer;
        this.count = count;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
