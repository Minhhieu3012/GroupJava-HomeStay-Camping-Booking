package ut.edu.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.database.models.Report;
import ut.edu.database.repositories.ReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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

    // Lấy báo cáo theo thời gian
    public List<Report> getReportsByPeriod(String period) {
        return reportRepository.findByPeriod(period);
    }

    // Tạo báo cáo mới
    public Report createReport(Report report) {
        if (report == null || report.getTotalRevenue() == null || report.getManagementFee() == null ||
                report.getOccupancyRate() == null || report.getReportDate() == null || report.getStatus() == null) {
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
            existingReport.setReportDate(updatedReport.getReportDate());
            existingReport.setStatus(updatedReport.getStatus());
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
