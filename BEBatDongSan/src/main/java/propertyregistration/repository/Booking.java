package propertyregistration.repository;

import com.example.models.Booking;
import com.example.models.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId); // Lấy lịch sử đặt của 1 user
    List<Booking> findByHomestayId(Long homestayId); // Lấy danh sách đặt của 1 homestay
    List<Booking> findByStatus(BookingStatus status); // Lọc đặt chỗ theo trạng thái
}
