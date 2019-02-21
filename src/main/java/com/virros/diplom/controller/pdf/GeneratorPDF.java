package com.virros.diplom.controller.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentParser;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.virros.diplom.model.*;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Controller
public class GeneratorPDF {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final BaseFont times = BaseFont.createFont("fonts/times.ttf", "Cp1251", BaseFont.EMBEDDED);
    private final BaseFont timesbd = BaseFont.createFont("fonts/timesbd.ttf", "Cp1251", BaseFont.EMBEDDED);
    private final BaseFont timesbi = BaseFont.createFont("fonts/timesbi.ttf", "Cp1251", BaseFont.EMBEDDED);
    private final BaseFont timesi = BaseFont.createFont("fonts/timesi.ttf", "Cp1251", BaseFont.EMBEDDED);

    private final Font headline1 = new Font(timesbd, 24);
    private final Font headline2 = new Font(timesbd, 18);
    private final Font text = new Font(times, 14);
    private final Font textb = new Font(timesbd, 14);
    private final Font texti = new Font(timesi, 14);

    public GeneratorPDF() throws IOException, DocumentException {
    }

    public ByteArrayOutputStream generatePdfToAccount(Applicant applicant) {
        Document document = new Document();
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter writer2 = PdfWriter.getInstance(document, baos);
            document.open();

            document.add(getHeadDocument());
            document.add(Chunk.NEWLINE);
            document.add(getInfoForAccountParagraf(applicant));

            if (applicant.getContacts() != null) {
                document.add(Chunk.NEWLINE);
                document.add(getContactsForAccountParagraf(applicant));
            }

            if (applicant.getEducations() != null) {
                document.add(Chunk.NEWLINE);
                document.add(getEducationsForAccountParagraf(applicant));
            }

            if (applicant.getExperiences() != null) {
                document.add(Chunk.NEWLINE);
                document.add(getExperiencesForAccountParagraf(applicant));
            }

            if (applicant.getSpecializations() != null) {
                document.add(Chunk.NEWLINE);
                document.add(getSpecializationsForAccountParagraf(applicant));
            }

            if (applicant.getLanguage_skills() != null) {
                document.add(Chunk.NEWLINE);
                document.add(getLanguagesForAccountParagraf(applicant));
            }

            if (applicant.getSport_skills() != null) {
                document.add(Chunk.NEWLINE);
                document.add(getSportsForAccountParagraf(applicant));
            }
            document.close();
            writer2.close();
            baos.close();

            return baos;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Paragraph getHeadDocument() {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setFont(headline1);
        paragraph.add("РЕЗЮМЕ");

        return paragraph;
    }

    private Paragraph getInfoForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Личная информация"));

        paragraph.add(getLine("Имя", applicant.getLast_name() + " " + applicant.getFirst_name() + " " +
                applicant.getFather_name()));
        paragraph.add(getLine("Пол", applicant.isSex() == true ? "Мужской" : "Женский"));
        paragraph.add(getLine("Дата рождения", applicant.getDate_of_birth().format(formatter) +
                " (возраст: " + Period.between(applicant.getDate_of_birth(), LocalDate.now()).getYears() + ")"));

        paragraph.add(Chunk.NEWLINE);

        if (applicant.getInfo().getAbout_me() != null) paragraph.add(
                getLine("Обо мне", applicant.getInfo().getAbout_me()));
        if (applicant.getInfo().getHobby() != null) paragraph.add(
                getLine("Хобби", applicant.getInfo().getHobby()));
        if (applicant.getInfo().getCitizenship() != null) paragraph.add(
                getLine("Гражданство", applicant.getInfo().getCitizenship()));
        if (applicant.getInfo().getCity() != null) paragraph.add(
                getLine("Город", applicant.getInfo().getCity()));
        if (applicant.getInfo().getAcademic_degree() != null) paragraph.add(
                getLine("Академическое звание", applicant.getInfo().getAcademic_degree()));
        if (applicant.getInfo().getFamily_status() != null) paragraph.add(
                getLine("Семейное положение", applicant.getInfo().getFamily_status()));
        if (applicant.getInfo().isChildren() != null) paragraph.add(
                getLine("Дети", applicant.getInfo().isChildren() ? "Имеются" : "Не имеются"));
        if (applicant.getInfo().getSalary() != null) paragraph.add(
                getLine("Желаемая зарплата", applicant.getInfo().getSalary().toString()));
        if (applicant.getInfo().isReady_to_move() != null) paragraph.add(
                getLine("Готов к переезду", applicant.getInfo().isReady_to_move() ? "Готов" : "Не готов"));
        if (applicant.getInfo().isReady_for_remove_work() != null) paragraph.add(
                getLine("Готов к удаленной работе", applicant.getInfo().isReady_for_remove_work() ? "Готов" : "Не готов"));

        return paragraph;
    }

    private Paragraph getContactsForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Контакты"));

        for (ContactApplicant contact : applicant.getContacts()) {
            paragraph.add(getLine(contact.getContact_type().getName(), contact.getValue()));
        }

        return paragraph;
    }

    private Paragraph getSpecializationsForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Профессиональные навыки"));

        for (SpecializationApplicant specialization : applicant.getSpecializations()) {
            paragraph.add(getLine(specialization.getField_of_activity().getName(), specialization.getSpecialization()));
        }

        return paragraph;
    }

    private Paragraph getLanguagesForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Языковые знания"));

        for (LanguageSkill language : applicant.getLanguage_skills()) {
            paragraph.add(getLine(language.getLanguage().getName(), language.getOwnership_level()));
        }

        return paragraph;
    }

    private Paragraph getSportsForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Спортивные навыки"));

        for (SportSkill skill : applicant.getSport_skills()) {
            paragraph.add(getLine(skill.getSport().getName(), skill.getOwnership_level()));
        }

        return paragraph;
    }

    private Element getEducationsForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Образование"));

        for (Education education : applicant.getEducations()) {
            Paragraph educ = new Paragraph();

            educ.add(Chunk.NEWLINE);
            educ.add(getLine("Образовательное учреждение", education.getEducational_institution()));
            educ.add(getLine("Факультет (Институт)", education.getFaculty()));
            educ.add(getLine("Специализация", education.getSpecialization()));
            educ.add(getLine("Года обучения", education.getDate_start().format(formatter) + " - " +
                    education.getDate_end().format(formatter)));
            educ.add(getLine("Форма обучения", education.getForm_training()));
            educ.add(getLine("Уровень образования", education.getLevel_education()));

            paragraph.add(educ);
        }

        return paragraph;
    }

    private Element getExperiencesForAccountParagraf(Applicant applicant) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getHeader("Опыт работы"));

        for (Experience experience : applicant.getExperiences()) {
            Paragraph exp = new Paragraph();

            exp.add(Chunk.NEWLINE);
            exp.add(getLine("Место работы", experience.getCompany_name()));
            exp.add(getLine("Позиция", experience.getPosition()));
            exp.add(getLine("Время работы", experience.getDate_start().format(formatter) + " - " +
                    experience.getDate_end() != null ? experience.getDate_end().format(formatter) : "Настоящее время"));
            exp.add(getLine("Обязанности", experience.getDuties()));
            exp.add(getLine("Достижения", experience.getAchievements()));

            paragraph.add(exp);
        }

        return paragraph;
    }

    private Paragraph getHeader(String name) {
        Paragraph header = new Paragraph(name, headline2);
        header.setAlignment(Element.ALIGN_CENTER);
        return header;
    }

    private Paragraph getLine(String name, String value) {
        Paragraph paragraph = new Paragraph();

        Chunk n = new Chunk(name + ": ", textb);
        Chunk v = new Chunk(value, text);
        paragraph.add(n);
        paragraph.add(v);

        return paragraph;
    }

}
