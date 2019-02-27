package com.virros.diplom.message.response;

public class CountVacancies {

    private Long id;
    private String name;
    private Integer count;

    public CountVacancies() {
    }

    public CountVacancies(Long id, String name, Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountVacancies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
