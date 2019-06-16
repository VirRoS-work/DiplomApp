package com.virros.diplom.message.request;

import com.virros.diplom.model.PageInfo;

public class VacancyFilter {

    private String key;
    private Boolean isGlobal;

    private PageInfo info;

    public PageInfo getInfo() {
        return info;
    }

    public void setInfo(PageInfo info) {
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(Boolean global) {
        isGlobal = global;
    }

    @Override
    public String toString() {
        return "VacancyFilter{" +
                "key='" + key + '\'' +
                ", isGlobal=" + isGlobal +
                ", info=" + info +
                '}';
    }
}
