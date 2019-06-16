package com.virros.diplom.repository;

import com.virros.diplom.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findTop10ByStatus(String status);
    List<Vacancy> findAllByStatus(String status);
    List<Vacancy> findAllByStatus(String status, Pageable pageable);

    @Query("SELECT COUNT(v) FROM Vacancy v " +
            "WHERE v.status = 'Активна'")
    Integer countFilterVacancies();

    @Query("SELECT v FROM Vacancy v " +
            "WHERE v.status = 'Активна'")
    List<Vacancy> findAllFilterVacancies(Pageable pageable);


    @Query("SELECT COUNT(v) FROM Vacancy v " +
            "WHERE v.status = 'Активна'" +
            "AND (v.title LIKE CONCAT('%', :key, '%') " +
            "OR (:isGlobal = true AND v.description LIKE CONCAT('%', :key, '%')))")
    Integer countFilterVacancies(@Param("key") String key, @Param("isGlobal") Boolean isGlogal);

    @Query("SELECT v FROM Vacancy v " +
            "WHERE v.status = 'Активна'" +
            "AND (v.title LIKE CONCAT('%', :key, '%') " +
            "OR (:isGlobal = true AND v.description LIKE CONCAT('%', :key, '%')))")
    List<Vacancy> findAllFilterVacancies(@Param("key") String key,
                                           @Param("isGlobal") Boolean isGlogal,
                                           Pageable pageable);


}
