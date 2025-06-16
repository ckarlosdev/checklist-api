package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.PreTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PreTaskDao extends JpaRepository<PreTask, Integer> {

    PreTask findByJobsIdAndDate(Integer jobsId, LocalDate date);

    @Query("""
        SELECT p FROM PreTask p
        LEFT JOIN FETCH p.activities
        WHERE p.preTasksId = :id
    """)
    Optional<PreTask> findByIdWithActivities(@Param("id")Integer id);


//    @Query("""
//        SELECT p FROM PreTask p
//        LEFT JOIN FETCH p.pretasksOption
//        WHERE p.preTasksId = :id
//    """)
//    Optional<PreTask> findByIdWithOptions(@Param("id")Integer id);

    @Query("SELECT p FROM PreTask p " +
            "LEFT JOIN FETCH p.pretasksOption o " +
            "LEFT JOIN FETCH o.checkboxOption " +
            "WHERE p.preTasksId = :id")
    Optional<PreTask> findByIdWithOptions(Integer id);


}
