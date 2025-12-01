package com.ck.wi.service.impl;

import com.ck.wi.model.dao.AttachmentDao;
import com.ck.wi.model.entity.Attachment;
import com.ck.wi.service.IAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttachmentImpl implements IAttachment {

    @Autowired
    private AttachmentDao attachmentDao;

    @Transactional
    @Override
    public Attachment save(Attachment attachment) {
        return attachmentDao.save(attachment);
    }

    @Transactional
    @Override
    public Attachment findById(Integer id) {
        return attachmentDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public List<Attachment> findAll() {
        return (List<Attachment>) attachmentDao.findAll();
    }
}
