package com.virros.diplom.repository;

import com.virros.diplom.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

    List<Event> findTop5ByTimeAfterOrderByTime(LocalDateTime time);
}
