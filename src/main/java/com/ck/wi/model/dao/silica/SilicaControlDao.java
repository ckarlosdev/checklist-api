package com.ck.wi.model.dao.silica;

import com.ck.wi.model.entity.silica.SilicaControl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SilicaControlDao extends CrudRepository<SilicaControl, Integer> {
}
