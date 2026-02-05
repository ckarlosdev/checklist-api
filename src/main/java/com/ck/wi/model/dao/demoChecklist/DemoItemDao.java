package com.ck.wi.model.dao.demoChecklist;

import com.ck.wi.model.entity.demoChecklist.DemoItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DemoItemDao extends CrudRepository<DemoItem, Integer> {
    @Query(
            value = "select * from demo_items where item_status='1' order by card_position, item_position;",
            nativeQuery = true
    )
    List<DemoItem> findItemsActives();
}
