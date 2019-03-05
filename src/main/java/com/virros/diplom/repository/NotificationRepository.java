package com.virros.diplom.repository;

import com.virros.diplom.model.Applicant;
import com.virros.diplom.model.Employer;
import com.virros.diplom.model.Notification;
import com.virros.diplom.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    @Query("SELECT n FROM Notification n INNER JOIN n.vacancy v WHERE v.employer = :employer")
    List<Notification> findAllByCompany(@Param("employer") Employer employer);

    @Query("SELECT n FROM Notification n INNER JOIN n.vacancy v WHERE v.employer = :employer AND n.status = 'Новый'")
    List<Notification> findAllNewNotificationByCompany(@Param("employer") Employer employer);

    boolean existsByApplicantAndVacancy(Applicant applicant, Vacancy vacancy);

}
