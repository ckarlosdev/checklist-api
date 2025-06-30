package com.ck.wi.model.dao.silica;

import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.silica.Silica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SilicaDao extends CrudRepository<Silica, Integer> {
    List<Silica> findByJobAndEventDate(Job job, LocalDate date);
}
