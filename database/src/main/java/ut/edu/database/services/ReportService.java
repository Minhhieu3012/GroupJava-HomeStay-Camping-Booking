package ut.edu.database.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ut.edu.database.dtos.MonthlyRevenueDTO;
import ut.edu.database.dtos.ReportDTO;
import ut.edu.database.mapper.ReportMapper;
import ut.edu.database.enums.ReportStatus;
import ut.edu.database.models.Report;
import ut.edu.database.models.Property;
import ut.edu.database.models.Booking;
import ut.edu.database.repositories.ReportRepository;
import ut.edu.database.repositories.PropertyRepository;
import ut.edu.database.repositories.BookingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final ReportMapper reportMapper;

    private static final BigDecimal MANAGEMENT_FEE_PERCENT = BigDecimal.valueOf(0.2);


    // Lấy tất cả báo cáo
    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy báo cáo theo ID
    public Optional<ReportDTO> getReportById(Long id) {  // Sửa từ List<Report> -> Optional<Report>
        return reportRepository.findById(id).map(reportMapper::toDTO);
    }


    // Loc theo propertyId
    public List<ReportDTO> getReportsByProperty(Long propertyId) {
        return reportRepository.findByPropertyId(propertyId)
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    //loc theo trang thai
    public List<ReportDTO> getReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status)
                .stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Tạo báo cáo theo property va khoang thoi gian
    public ReportDTO createReport(Long propertyId, LocalDate startDate, LocalDate endDate) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        List<Booking> bookings = bookingRepository.findByPropertyId(propertyId).stream()
                .filter(b -> !b.getStartDate().isAfter(endDate) && !b.getEndDate().isBefore(startDate))
                .toList();
        BigDecimal totalRevenue = bookings.stream()
                .map(Booking::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal managementFee = totalRevenue.multiply(MANAGEMENT_FEE_PERCENT);
        long totalDays = startDate.until(endDate).getDays()+1;
        long bookedDays = bookings.stream()
                .mapToLong(b -> {
                    LocalDate from = b.getStartDate().isBefore(startDate) ? startDate : b.getStartDate();
                    LocalDate to = b.getEndDate().isAfter(endDate) ? endDate : b.getEndDate();
                    return from.until(to).getDays() + 1;
                })
                .sum();

        BigDecimal occupancyRate = totalDays > 0
                ? BigDecimal.valueOf(bookedDays)
                .divide(BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        Report report = Report.builder()
                .property(property)
                .totalRevenue(totalRevenue)
                .managementFee(managementFee)
                .occupancyRate(occupancyRate)
                .startDate(startDate)
                .endDate(endDate)
                .reportDate(LocalDate.now())
                .status(ReportStatus.APPROVED)
                .build();

        return reportMapper.toDTO(reportRepository.save(report));
    }

    // Xóa báo cáo theo ID
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public List<MonthlyRevenueDTO> getMonthlyRevenue(int year, Long ownerId, boolean forAdmin) {
        List<Object[]> rows = bookingRepository.getMonthlyRevenue(year, ownerId, forAdmin);
        return rows.stream()
                .map(r->new MonthlyRevenueDTO((Integer) r[0], (BigDecimal) r[1]))
                .collect(Collectors.toList());
    }
}
