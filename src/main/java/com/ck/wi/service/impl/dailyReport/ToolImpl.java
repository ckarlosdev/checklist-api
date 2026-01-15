package com.ck.wi.service.impl.dailyReport;

import com.ck.wi.model.dao.dailyReport.ToolDao;
import com.ck.wi.model.dto.dailyReport.creation.DrToolCreateDto;
import com.ck.wi.model.entity.dailyReport.Tool;
import com.ck.wi.service.dailyReport.ITool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolImpl implements ITool {

    @Autowired
    private ToolDao toolDao;

    @Override
    public List<Tool> findByDailyReportId(Integer dailyReportId){
        return (List<Tool>) toolDao.findByDailyReportIdAndStatus(dailyReportId, "1");
    }

    @Override
    public List<Tool> findByDailyReportIds(List<Integer> dailyReportIds){
        return (List<Tool>) toolDao.findByDailyReportIdIn(dailyReportIds);
    }

    @Override
    public List<DrToolCreateDto> getLastReportTools(String jobNum){
        List<Tool> tools = toolDao.findToolsFromLastReport(jobNum);

        return tools.stream()
                .map(this::toDto)
                .toList();
    }

    private DrToolCreateDto toDto(Tool tool){

        return DrToolCreateDto.builder()
                .drToolId(tool.getDrToolId())
                .qty(tool.getQty())
                .name(tool.getName())
                .other(tool.getOther())
                .comments(tool.getComments())
                .build();
    }
}
