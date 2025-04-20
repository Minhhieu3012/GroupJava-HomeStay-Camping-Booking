package admin.bookinghomecamping.controller;

import admin.bookinghomecamping.dto.RevenueReportDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/admin")
public class AdminReportController {

    @GetMapping("/revenue-report")
    public ArrayList<Object> getRevenueReport() {
        ArrayList<Object> mockData = new ArrayList<>();
        mockData.add(new RevenueReportDTO(1, 10000000));
        mockData.add(new RevenueReportDTO(2, 8500000));
        mockData.add(new RevenueReportDTO(3, 12500000));
        mockData.add(new RevenueReportDTO(4, 1000000));
        mockData.add(new RevenueReportDTO(5, 1000000));
        mockData.add(new RevenueReportDTO(6, 20000000));
        mockData.add(new RevenueReportDTO(7, 5000000));
        mockData.add(new RevenueReportDTO(8, 68522222));
        mockData.add(new RevenueReportDTO(9, 12400000));
        mockData.add(new RevenueReportDTO(10, 16500000));
        mockData.add(new RevenueReportDTO(11, 15580000));
        mockData.add(new RevenueReportDTO(12, 42000000));
        return mockData;
    }
}

