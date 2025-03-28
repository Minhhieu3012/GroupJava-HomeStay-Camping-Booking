package ut.edu.database.services;
import ut.edu.database.models.Report;
import ut.edu.database.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    //
    public List<Report> getReportsByPropertyId(Long propertyId) {
        return reportRepository.findByPropertyId(propertyId);
    }
    //
    public List<Report> getReportsByPeriod(String period) {
        return reportRepository.findByPeriod(period);
    }
    //
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }
}
