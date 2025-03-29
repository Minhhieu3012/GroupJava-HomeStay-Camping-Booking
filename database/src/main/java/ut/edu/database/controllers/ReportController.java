package ut.edu.database.controllers;
import ut.edu.database.models.Report;
import ut.edu.database.repositories.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportRepository reportRepository;

    //lay tat ca bao cao
    @GetMapping
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    //lay bao cao theo id
    @GetMapping("/{id}")
    public Optional<Report> getReportById(@PathVariable Long id) {
        return reportRepository.findById(id);
    }
    //tao bao cao moi
    @PostMapping
    public Report createReport(@RequestBody Report report) {
        return reportRepository.save(report);
    }
    //cap nhat bao cao
    @PutMapping("/{id}")
    public Report updateReport(@PathVariable Long id, @RequestBody Report report) {
        return reportRepository.save(report);
    }
    //delete bao cao
    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportRepository.deleteById(id);
    }
}
