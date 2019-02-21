package com.virros.diplom.repository;

import com.virros.diplom.model.SpecializationApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationApplicantRepository extends JpaRepository<SpecializationApplicant, Long>{
}
