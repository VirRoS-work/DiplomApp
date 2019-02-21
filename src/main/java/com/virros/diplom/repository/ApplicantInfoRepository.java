package com.virros.diplom.repository;

import com.virros.diplom.model.Applicant;
import com.virros.diplom.model.ApplicantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantInfoRepository extends JpaRepository<ApplicantInfo, Long>{
    public Optional<ApplicantInfo> findByApplicant(Applicant applicant);
}
