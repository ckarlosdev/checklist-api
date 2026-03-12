package com.ck.wi.service;

import com.ck.wi.model.dto.AttachmentDto;
import com.ck.wi.model.entity.Attachment;

import java.util.List;

public interface IAttachment {

    Attachment save(AttachmentDto attachmentDto);

    Attachment findById(Integer id);

    List<Attachment> findAll();
}
