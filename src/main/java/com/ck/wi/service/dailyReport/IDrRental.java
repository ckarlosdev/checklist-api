package com.ck.wi.service.dailyReport;

import com.ck.wi.model.dto.dailyReport.creation.DrRentalCreateDto;
import com.ck.wi.model.entity.dailyReport.DrRental;

import java.util.List;

public interface IDrRental {

    List<DrRentalCreateDto> getLastReportRentals(String jobNum);
}
