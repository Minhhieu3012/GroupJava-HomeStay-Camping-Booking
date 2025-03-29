package ut.edu.database.services;
import ut.edu.database.models.Report;
import ut.edu.database.repositories.ReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {
    //Constructor Injection (de test hon - khong can spring context)
    //dam bao khong bi null vi no duoc yeu cau ngay khi khoi tao
    private final ReportRepository reportRepository;
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    //lay ds bao cao theo id tai san
    public List<Report> getReportsByPropertyId(Long propertyId) {
        if(propertyId == null || propertyId <= 0){
            throw new IllegalArgumentException("Invalid property id");
        }
        return reportRepository.findByPropertyId(propertyId);
    }
    //lay ds bao cao theo khoang thoi gian (period)
    public List<Report> getReportsByPeriod(String period) {
        if(period == null || period.isBlank()){
            throw new IllegalArgumentException("Invalid period");
        }
        return reportRepository.findByPeriod(period);
    }
    //tao bao cao moi
    public Report createReport(Report report) {
        if(report == null){
            throw new IllegalArgumentException("Invalid report");
        }
        return reportRepository.save(report);
    }
}
