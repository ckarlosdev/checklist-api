package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.Activity;
import com.ck.wi.model.entity.hazard.PretasksOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PretasksOptionDao extends CrudRepository<PretasksOption, Integer> {
    @Query("SELECT p FROM PretasksOption p WHERE p.preTask.preTasksId = :preTaskId AND p.status = :status")
    List<PretasksOption> findByPreTaskIdAndStatus(
            @Param("preTaskId") Integer preTaskId,
            @Param("status") String status
    );
}
