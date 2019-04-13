package com.virros.diplom.repository;

import com.virros.diplom.model.Applicant;
import com.virros.diplom.model.Bookmark;
import com.virros.diplom.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByApplicant(Applicant applicant);
    Optional<Bookmark> findByApplicantAndVacancy(Applicant applicant, Vacancy vacancy);
    boolean existsByApplicantAndVacancy(Applicant applicant, Vacancy vacancy);
    Long countBookmarksByVacancy(Vacancy vacancy);
}
