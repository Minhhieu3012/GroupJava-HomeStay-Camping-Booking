//DỮ LIỆU THẬT BAOCAODOANHTHU

//package admin.bookinghomecamping.repository;
//
//import admin.bookinghomecamping.dto.RevenueReportDTO;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface BookingRepository extends JpaRepository<Booking, Long> {
//    @Query("SELECT new admin.bookinghomecamping.dto.RevenueReportDTO(MONTH(b.createdAt), SUM(b.totalPrice)) " +
//            "FROM Booking b GROUP BY MONTH(b.createdAt)")
//    List<RevenueReportDTO> getMonthlyRevenue();
//}
//
//
