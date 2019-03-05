package com.virros.diplom.repository;

import com.virros.diplom.model.Employer;
import com.virros.diplom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> getEmployerByUser(User user);

    @Query("SELECT e FROM Employer e INNER JOIN e.user u WHERE u.blocked = true")
    List<Employer> findAllByBlockedUser();
}
