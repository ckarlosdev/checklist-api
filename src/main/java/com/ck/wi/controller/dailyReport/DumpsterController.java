package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.DumpsterDto;
import com.ck.wi.model.dto.request.DumpsterRequest;
import com.ck.wi.model.entity.dailyReport.Dumpster;
import com.ck.wi.service.dailyReport.IDumpster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io/Dashboard"
})
@RestController
@RequestMapping("/api/v1")
public class DumpsterController {
    @Autowired
    private IDumpster dumpsterService;

    @GetMapping("dumpster/dailyReport/{dailyReportId}")
    public DumpsterDto getDumpstersByDailyReportId(@PathVariable Integer dailyReportId){
        Dumpster dumpster = dumpsterService.findByDailyReportId(dailyReportId);

        return DumpsterDto.builder()
                .drDumpstersId(dumpster.getDrDumpstersId())
                .dailyReportId(dumpster.getDailyReportId())
                .concret40(dumpster.getConcret40())
                .concret35(dumpster.getConcret35())
                .concret30(dumpster.getConcret30())
                .concret20(dumpster.getConcret20())
                .concret12(dumpster.getConcret12())
                .metal40(dumpster.getMetal40())
                .metal35(dumpster.getMetal35())
                .metal30(dumpster.getMetal30())
                .metal20(dumpster.getMetal20())
                .metal12(dumpster.getMetal12())
                .cd40(dumpster.getCd40())
                .cd35(dumpster.getCd35())
                .cd30(dumpster.getCd30())
                .cd20(dumpster.getCd20())
                .cd12(dumpster.getCd12())
                .status(dumpster.getStatus())
                .concretQuad(dumpster.getConcretQuad())
                .concretSemi(dumpster.getConcretSemi())
                .concretGondola(dumpster.getConcretGondola())
                .metalQuad(dumpster.getMetalQuad())
                .metalSemi(dumpster.getMetalSemi())
                .metalGondola(dumpster.getMetalGondola())
                .cdQuad(dumpster.getCdQuad())
                .cdSemi(dumpster.getCdSemi())
                .cdGondola(dumpster.getCdGondola())
                .build();
    }

    @GetMapping("dumpster/dailyReports")
    public List<DumpsterDto> getDumpstersByDailyReportIds(@RequestBody DumpsterRequest request){
        List<Dumpster> dumpsters = dumpsterService.findByDailyReportIds(request.getDailyReportIds());

        return dumpsters.stream()
                .map( dumpster ->
                        DumpsterDto.builder()
                                .drDumpstersId(dumpster.getDrDumpstersId())
                                .dailyReportId(dumpster.getDailyReportId())
                                .concret40(dumpster.getConcret40())
                                .concret35(dumpster.getConcret35())
                                .concret30(dumpster.getConcret30())
                                .concret20(dumpster.getConcret20())
                                .concret12(dumpster.getConcret12())
                                .metal40(dumpster.getMetal40())
                                .metal35(dumpster.getMetal35())
                                .metal30(dumpster.getMetal30())
                                .metal20(dumpster.getMetal20())
                                .metal12(dumpster.getMetal12())
                                .cd40(dumpster.getCd40())
                                .cd35(dumpster.getCd35())
                                .cd30(dumpster.getCd30())
                                .cd20(dumpster.getCd20())
                                .cd12(dumpster.getCd12())
                                .status(dumpster.getStatus())
                                .concretQuad(dumpster.getConcretQuad())
                                .concretSemi(dumpster.getConcretSemi())
                                .concretGondola(dumpster.getConcretGondola())
                                .metalQuad(dumpster.getMetalQuad())
                                .metalSemi(dumpster.getMetalSemi())
                                .metalGondola(dumpster.getMetalGondola())
                                .cdQuad(dumpster.getCdQuad())
                                .cdSemi(dumpster.getCdSemi())
                                .cdGondola(dumpster.getCdGondola())
                                .build())
                .collect(Collectors.toList());
    }
}
