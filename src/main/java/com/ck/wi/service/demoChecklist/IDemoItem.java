package com.ck.wi.service.demoChecklist;

import com.ck.wi.model.entity.demoChecklist.DemoItem;

import java.util.List;

public interface IDemoItem {
    List<DemoItem> findAll();
    List<DemoItem> findActives();
}
