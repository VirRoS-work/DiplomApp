package com.virros.diplom.repository;

import com.virros.diplom.model.ContactApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactApplicantRepository extends JpaRepository<ContactApplicant, Long> {
}
