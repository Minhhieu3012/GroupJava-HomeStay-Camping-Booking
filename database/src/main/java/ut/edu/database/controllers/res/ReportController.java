package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ut.edu.database.dtos.MonthlyRevenueDTO;
import ut.edu.database.dtos.ReportDTO;
import ut.edu.database.enums.ReportStatus;
import ut.edu.database.services.ReportService;
import ut.edu.database.services.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    //ADMIN: lay tat ca bao cao
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());  // Lấy tất cả reports
    }

    //ADMIN: lay bao cao theo ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportDTO> getById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //ADMIN: Tao bao cao
    //goi service tao report tu ngay A -> B cho 1 property
    //su dung RequestParam de nhan tham so tu url/postman
    //vd: POST http://localhost:8080/api/reports/create?propertyId=2&startDate=2025-04-01&endDate=2025-05-31
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportDTO> createReport(
            @RequestParam Long propertyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.createReport(propertyId, startDate, endDate));
    }

    //ADMIN: loc theo trang thai
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDTO>> getByStatus(@PathVariable ReportStatus status) {
        return ResponseEntity.ok(reportService.getReportsByStatus(status));
    }

    //ADMIN: xoa report
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build(); //tra ve loi 204 no content khi xoa thanh cong
    }
    @GetMapping("/revenue/monthly")
    public ResponseEntity<List<MonthlyRevenueDTO>> getMonthlyRevenue(
        @RequestParam int year,
        @RequestParam boolean forAdmin,
        @RequestParam(required = false) String usernameOrEmail){

        Long ownerId = null;
        if(!forAdmin && usernameOrEmail != null){
            ownerId = userService.getUserIdByUsername(usernameOrEmail);
        }
        return ResponseEntity.ok(reportService.getMonthlyRevenue(year, ownerId, forAdmin));
    }
}
