package com.example.homestay.repository;

import com.example.models.Booking;
import com.example.models.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
        "FROM Booking b WHERE b.homestay.id = :homestayId " +
        "AND b.startDate <= :endDate AND b.endDate >= :startDate")
boolean existsByHomestayIdAndDateRangeOverlap(
        @Param("homestayId") Long homestayId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
);

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId); // Lấy lịch sử đặt của 1 user
    List<Booking> findByHomestayId(Long homestayId); // Lấy danh sách đặt của 1 homestay
    List<Booking> findByStatus(BookingStatus status); // Lọc đặt chỗ theo trạng thái
}
