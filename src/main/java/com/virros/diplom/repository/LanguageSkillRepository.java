package com.virros.diplom.repository;

import com.virros.diplom.model.LanguageSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageSkillRepository extends JpaRepository<LanguageSkill, Long> {
}
