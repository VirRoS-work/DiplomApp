package com.virros.diplom.repository;

import com.virros.diplom.model.ContactPersonContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPersonContactRepository extends JpaRepository<ContactPersonContact, Long>{
}
