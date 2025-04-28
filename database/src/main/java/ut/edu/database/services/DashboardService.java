package ut.edu.database.services;

import ut.edu.database.dtos.*;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ut.edu.database.enums.PropertyStatus;

@Service
public class DashboardService {

    private final BookingService bookingService;
    private final ReportService reportService;
    private final PropertyService propertyService;
    private final UserService userService;
    private final PaymentService paymentService;

    public DashboardService(BookingService bookingService, ReportService reportService, PropertyService propertyService, UserService userService, PaymentService paymentService) {
        this.bookingService = bookingService;
        this.reportService = reportService;
        this.propertyService = propertyService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    public DashboardDTO getDashboardData() {
        LocalDate today = LocalDate.now();
        List<BookingDTO> bookings = bookingService.getAllBookingDTOs();
        List<PropertyDTO> properties = propertyService.getAllPropertyDTOs();
        List<UserDTO> users = userService.getAllUsers();
        List<ReportDTO> reports = reportService.getAllReports();
        List<PaymentDTO> payments = paymentService.getAllPayments();

//         Tổng số đơn đặt phòng hôm nay
        Long totalBookingsToday = bookings.stream()
                .filter(b -> b.getStartDate() != null && b.getStartDate().equals(today))
                .count();


        // Tổng doanh thu hôm nay
        BigDecimal totalRevenueToday = bookings.stream()
                .filter(b -> b.getStartDate() != null && b.getStartDate().equals(today))
                .map(BookingDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tổng doanh thu tháng này
        int currentMonth = today.getMonthValue();
        BigDecimal totalRevenueThisMonth = payments.stream()
                .filter(r -> r.getPaymentDate() != null && r.getPaymentDate().getMonthValue() == currentMonth)
                .map(PaymentDTO::getAdminFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Số lượng phòng đang được đặt (Booking đang active)
        Long totalRoomsBooked = properties.stream()
                .filter(b -> b.getStatus() != null && b.getStatus().name().equalsIgnoreCase("BOOKED"))
                .count();

        // Số lượng phòng trống = Tổng số homestay - số lượng phòng đã được đặt
        Long totalRoomsAvailable = properties.size() - totalRoomsBooked;

        // Số lượng người dùng
        Long totalUsers = users.stream()
                .filter(u -> u.getRole() != null && u.getRole().name().equalsIgnoreCase("CUSTOMER"))
                .count();

        // Số lượng homestay
        Long totalHomestays = properties.stream()
                .filter(p -> p.getStatus() != null &&
                        (p.getStatus() == PropertyStatus.AVAILABLE || p.getStatus() == PropertyStatus.BOOKED))
                .count();

        return new DashboardDTO(
                totalBookingsToday,
                totalRevenueToday,
                totalRevenueThisMonth,
                totalRoomsBooked,
                totalRoomsAvailable,
                totalUsers,
                totalHomestays
        );
    }
}
