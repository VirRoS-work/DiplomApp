package com.virros.diplom.repository;

import com.virros.diplom.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    public Optional<ContactType> getContactTypeByName(String name);
}
