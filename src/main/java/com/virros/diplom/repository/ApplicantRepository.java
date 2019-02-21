package com.virros.diplom.repository;

import com.virros.diplom.model.Applicant;
import com.virros.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long>{
    Optional<Applicant> getApplicantByUser(User user);
}