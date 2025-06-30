package com.ck.wi.model.dao.silica;

import com.ck.wi.model.entity.hazard.PreTask;
import com.ck.wi.model.entity.silica.Control;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ControlDao extends CrudRepository<Control, Integer> {

//    @Query("""
//        SELECT c FROM Control c
//        LEFT JOIN FETCH c.descriptions
//        WHERE c.controlsId = :id
//    """)
//    Optional<Control> findByIdWithDescriptions(@Param("id")Integer id);
}
