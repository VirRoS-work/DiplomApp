package com.virros.diplom.controller;

import com.virros.diplom.model.Applicant;
import com.virros.diplom.model.Employer;
import com.virros.diplom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MyMailSender {

    @Autowired
    private JavaMailSender sender;

    @Async
    public void sendMailRegistrationNotification(Applicant applicant) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(applicant.getUser().getEmail());
            helper.setSubject("Успешная регистрация");
            helper.setText("Здравствуйте, " + applicant.getLast_name() + " " + applicant.getFirst_name() + "!\n\n" +
                    "Мы рады сообщить, что Вы успешно зарегистрированы на сайте! \n" +
                    "Не забывайте заполнить о себе как можно больше информации, чтобы работодателям было проще Вас найти. " +
                    "Желаем найти наилучшую вакансию в самые короткие сроки.\n\n" +
                    "Всего доброго.");

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMailRegistrationNotification(Employer employer) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(employer.getUser().getEmail());
            helper.setSubject("Заявка на регистрацию");
            helper.setText("Здравствуйте, " + employer.getName() + "!\n\n" +
                    "Ваша заявка принята на рассмотрение. Как только заявка будет одобрена, Вы получите письмо и " +
                    "доступ к сайту.\n\n" +
                    "Всего доброго.");

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMailErrorRegistrationNotification(Employer employer) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(employer.getUser().getEmail());
            helper.setSubject("Ошибка регистрации");
            helper.setText("Здравствуйте, " + employer.getName() + "!\n\n" +
                    "К сожаления, администрация сайта не одобрила заявку на регистрацию вашей компании! \n" +
                    "Попробуйте внести корректные данные и отправить заявку снова.\n\n" +
                    "Всего доброго.");

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMailSuccessfullyRegistrationNotification(Employer employer) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(employer.getUser().getEmail());
            helper.setSubject("Успешная регистрация");
            helper.setText("Здравствуйте, " + employer.getName() + "!\n\n" +
                    "Мы рады сообщить, что Ваша компания успешно зарегистрирована на сайте! \n" +
                    "Не забывайте заполнить как можно больше информации, чтобы соискателям было проще Вас найти. " +
                    "Желаем найти лучших сотрудников в самые короткие сроки.\n\n" +
                    "Всего доброго.");

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMailResetPasswordNotification(String mail) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(mail);
            helper.setSubject("Изменение пароля");
            helper.setText("Здравствуйте!\n\n" +
                    "Ваш пароль был успешно изменен.\n" +
                    "Если это были не Вы, обратитесь к администрации\n\n" +
                    "Всего доброго.");

            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
