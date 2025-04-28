package ut.edu.database.repositories;

import org.springframework.data.jpa.repository.Query;
import ut.edu.database.enums.PaymentStatus;
import ut.edu.database.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByPaymentStatus(PaymentStatus status);

    @Query("SELECT FUNCTION('MONTH', p.paymentDate), SUM(p.totalAmount) FROM Payment p " +
            "WHERE FUNCTION('YEAR', p.paymentDate) = :year AND p.paymentStatus = 'COMPLETED' " +
            "GROUP BY FUNCTION('MONTH', p.paymentDate)")
    List<Object[]> getMonthlyRevenueByPayment(int year);
}
