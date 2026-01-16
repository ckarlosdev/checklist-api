package com.ck.wi.model.dao.silica;

import com.ck.wi.model.dto.hazard.create.PretaskViewDto;
import com.ck.wi.model.dto.silica.SilicaShortViewDto;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.silica.Silica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SilicaDao extends CrudRepository<Silica, Integer> {

    List<Silica> findByJobAndEventDate(Job job, LocalDate date);

    List<Silica> findByJobOrderByEventDateDesc(Job job);


    @Query(value = " select * from silica " +
            "where jobs_id = :id and silica_status='1' " +
            "order by event_date desc;"
            , nativeQuery = true)
    List<SilicaShortViewDto> findSilicaByJobId(Integer id);

}
