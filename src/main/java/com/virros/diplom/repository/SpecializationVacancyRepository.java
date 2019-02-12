package com.virros.diplom.repository;

import com.virros.diplom.model.SpecializationVacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationVacancyRepository extends JpaRepository<SpecializationVacancy, Long>{
}
