package com.ck.wi.service.impl.demoChecklist;

import com.ck.wi.model.dao.demoChecklist.DemoItemDao;
import com.ck.wi.model.entity.demoChecklist.DemoItem;
import com.ck.wi.service.demoChecklist.IDemoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoItemItem implements IDemoItem {

    @Autowired
    private DemoItemDao demoItemDao;

    @Override
    public List<DemoItem> findAll(){
        return (List<DemoItem>) demoItemDao.findAll();
   }

   @Override
   public List<DemoItem> findActives(){
        return (List<DemoItem>) demoItemDao.findItemsActives();
   }
}
