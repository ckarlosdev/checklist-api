package com.ck.wi.controller.demoChecklist;

import com.ck.wi.model.entity.demoChecklist.DemoItem;
import com.ck.wi.service.demoChecklist.IDemoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class DemoItemController {
    @Autowired
    private IDemoItem demoItemService;

    @GetMapping("/demoItems")
    public List<DemoItem> getAllItems(){
        return (List<DemoItem>) demoItemService.findAll();
    }

    @GetMapping("/demoItems/actives")
    public List<DemoItem> getItemsActives(){
        return (List<DemoItem>) demoItemService.findActives();
    }
}
