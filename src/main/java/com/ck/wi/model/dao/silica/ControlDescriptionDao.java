package com.ck.wi.model.dao.silica;

import com.ck.wi.model.entity.silica.Control;
import com.ck.wi.model.entity.silica.ControlsDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlDescriptionDao extends CrudRepository<ControlsDescription, Integer> {
    List<ControlsDescription> findByControl(Control control);
}
