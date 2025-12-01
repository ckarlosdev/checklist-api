package com.ck.wi.controller;

import com.ck.wi.model.entity.Attachment;
import com.ck.wi.service.IAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class AttachmentController {

    @Autowired
    private IAttachment attachmentService;

    @PostMapping("attachment")
    public Attachment create (@RequestBody Attachment attachment) {
        return attachmentService.save(attachment);
    }

    @GetMapping("attachment/{id}")
    public Attachment showById(@PathVariable Integer id){
        return attachmentService.findById(id);
    }

    @GetMapping("attachments")
    public List<Attachment> showAll(){
        return attachmentService.findAll();
    }
}
