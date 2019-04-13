package com.virros.diplom.repository;

import com.virros.diplom.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findTop10ByStatus(String status);
    List<Vacancy> findAllByStatus(String status);
    List<Vacancy> findAllByStatus(String status, Pageable pageable);
}
