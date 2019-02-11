package com.virros.diplom.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Constants {

    private final List<String> typesConpany = Arrays.asList("ИП", "ООО", "ЗАО", "ОАО");

    private final List<String> countEmployeesConpany = Arrays.asList("Менее 50", "От 50 до 250", "Более 250");

    public List<String> getTypesConpany() {
        return typesConpany;
    }

    public List<String> getCountEmployeesConpany() {
        return countEmployeesConpany;
    }
}
