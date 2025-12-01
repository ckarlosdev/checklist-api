package com.ck.wi.model.dao;


import com.ck.wi.model.entity.Attachment;
import com.ck.wi.model.entity.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao extends CrudRepository<Attachment, Integer> {
}
