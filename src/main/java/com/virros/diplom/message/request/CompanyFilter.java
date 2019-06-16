package com.virros.diplom.message.request;

import com.virros.diplom.model.PageInfo;

public class CompanyFilter {

    private String name;
    private Boolean isEmptyCompany;

    private PageInfo info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEmptyCompany() {
        return isEmptyCompany;
    }

    public void setIsEmptyCompany(Boolean isEmptyCompany) {
        this.isEmptyCompany = isEmptyCompany;
    }

    public PageInfo getInfo() {
        return info;
    }

    public void setInfo(PageInfo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "CompanyFilter{" +
                "name='" + name + '\'' +
                ", isEmptyCompany=" + isEmptyCompany +
                ", info=" + info +
                '}';
    }
}
