package com.ck.wi.service.impl;

import com.ck.wi.model.dao.AttachmentDao;
import com.ck.wi.model.dto.AttachmentDto;
import com.ck.wi.model.entity.Attachment;
import com.ck.wi.model.entity.Equipment;
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
    public Attachment save(AttachmentDto attachmentDto) {
        Attachment attachment;

        if(attachmentDto.getAttachmentsId() != null){
            attachment = attachmentDao.findById(attachmentDto.getAttachmentsId())
                    .orElseThrow(() -> new RuntimeException("attachment not found"));
        }else{
            attachment = new Attachment();
            attachment.setCreatedBy(attachmentDto.getUser());
        }

        attachment.setFamily(attachmentDto.getFamily());
        attachment.setNumber(attachmentDto.getNumber());
        attachment.setName(attachmentDto.getName());
        attachment.setManufacturing(attachmentDto.getManufacturing());
        attachment.setModel(attachmentDto.getModel());
        attachment.setYear(attachmentDto.getYear());
        attachment.setPurchaseDate(attachmentDto.getPurchaseDate());
        attachment.setStatus(attachmentDto.getStatus());
        attachment.setConditions(attachmentDto.getConditions());
        attachment.setSerialNumber(attachmentDto.getSerialNumber());
        attachment.setAttachmentStatus("1");
        attachment.setUpdatedBy(attachmentDto.getUser());

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
