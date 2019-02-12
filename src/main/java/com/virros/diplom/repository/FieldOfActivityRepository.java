package com.virros.diplom.repository;

import com.virros.diplom.model.FieldOfActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldOfActivityRepository extends JpaRepository<FieldOfActivity, Long>{
}
