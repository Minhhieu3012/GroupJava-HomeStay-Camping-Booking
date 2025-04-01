package ut.edu.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.database.models.Report;
import ut.edu.database.repositories.ReportRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // Thay thế phương thức sử dụng findByPeriod
    public List<Report> getReportsByDateRange(LocalDate start, LocalDate end) {
        return reportRepository.findByStartDateBetween(start, end);
    }

    // Lấy tất cả báo cáo
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Lấy báo cáo theo ID
    public Optional<Report> getReportById(Long id) {  // Sửa từ List<Report> -> Optional<Report>
        return reportRepository.findById(id);
    }

    // Lấy báo cáo theo propertyId
    public List<Report> getReportsByPropertyId(Long propertyId) {
        return reportRepository.findByPropertyId(propertyId);
    }

    public List<Report> getReportsByStatus(Report.ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    // Tạo báo cáo mới
    public Report createReport(Report report, Long propertyId) {
        if (report.getProperty() == null
                || report.getTotalRevenue() == null
                || report.getManagementFee() == null
                || report.getOccupancyRate() == null
                || report.getReportDate() == null
                || report.getStatus() == null
                || report.getStartDate() == null
                || report.getEndDate() == null
                || report.getStartDate().isAfter(report.getEndDate())  // Kiểm tra ngày
        ) {
            throw new IllegalArgumentException("Invalid report!");
        }
        return reportRepository.save(report);
    }

    // Cập nhật báo cáo
    public Optional<Report> updateReport(Long id, Report updatedReport) {
        return reportRepository.findById(id).map(existingReport -> {
            existingReport.setTotalRevenue(updatedReport.getTotalRevenue());
            existingReport.setManagementFee(updatedReport.getManagementFee());
            existingReport.setOccupancyRate(updatedReport.getOccupancyRate());
            existingReport.setStatus(updatedReport.getStatus());
            existingReport.setReportDate(updatedReport.getReportDate());
            existingReport.setStartDate(updatedReport.getStartDate());
            existingReport.setEndDate(updatedReport.getEndDate());
            return reportRepository.save(existingReport);
        });
    }

    // Xóa báo cáo theo ID
    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
