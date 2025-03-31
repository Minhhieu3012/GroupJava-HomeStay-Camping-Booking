package ut.edu.database.controllers;
import ut.edu.database.models.Report;
import ut.edu.database.services.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    //Constructor Injection
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //lay tat ca bao cao
    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();  // Lấy tất cả reports
    }

    @GetMapping("/filter")
    public List<Report> getReportsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ){
            return reportService.getReportsByDateRange(startDate, endDate);
    }

    // Thêm endpoint mới (nếu cần)
    @GetMapping("/status/{status}")
    public List<Report> getReportsByStatus(@PathVariable Report.ReportStatus status) {
        return reportService.getReportsByStatus(status);
    }


    //lay bao cao theo id
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //tao bao cao moi
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report, Long propertyId) {
        try{
            Report savedReport = reportService.createReport(report, propertyId);
            return ResponseEntity.ok(savedReport); //200 ok
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); //bao loi 404 khi du lieu ko hop le
        }
    }

    //cap nhat bao cao theo id
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        try{
            return reportService.updateReport(id,updatedReport)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //delete bao cao
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if(reportService.deleteReport(id)) {
            return ResponseEntity.noContent().build(); //204 no content khi xoa thanh cong
        }
        return ResponseEntity.notFound().build();
    }
}
