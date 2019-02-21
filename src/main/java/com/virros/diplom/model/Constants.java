package com.virros.diplom.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Constants {

    private final List<String> typesConpany = Arrays.asList("ИП", "ООО", "ЗАО", "ОАО");

    private final List<String> countEmployeesConpany = Arrays.asList("Менее 50", "От 50 до 250", "Более 250");

    private final List<String> typesEmployment = Arrays.asList("Полный", "Неполный");

    private final List<String> familyStatus = Arrays.asList("Не замужем/не женат", "Разведен/разведена", "Замужем/женат");

    private final List<String> formTraining = Arrays.asList("Очная", "Очно-Заочная", "Заочная");

    public List<String> getTypesConpany() {
        return typesConpany;
    }

    public List<String> getFamilyStatus() {
        return familyStatus;
    }

    public List<String> getCountEmployeesConpany() {
        return countEmployeesConpany;
    }

    public List<String> getTypesEmployment() {
        return typesEmployment;
    }

    public List<String> getFormTraining() {
        return formTraining;
    }
}
