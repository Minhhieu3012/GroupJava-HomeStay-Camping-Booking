package ut.edu.database.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.database.models.Booking;
import java.util.List;
import ut.edu.database.enums.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByPropertyId(Long propertyId);
    List<Booking> findByStatus(BookingStatus status); //enum truc tiep

    @Query("""
    SELECT MONTH(b.startDate) AS month, 
           SUM(CASE WHEN :forAdmin = true THEN b.adminFee ELSE b.ownerEarning END) AS total 
    FROM Booking b
    WHERE YEAR(b.startDate) = :year
    AND (:ownerId IS NULL OR b.property.owner.id = :ownerId)
    GROUP BY MONTH(b.startDate)
    ORDER BY MONTH(b.startDate)
""")
    List<Object[]> getMonthlyRevenue(
            @Param("year") int year,
            @Param("ownerId") Long ownerId,
            @Param("forAdmin") boolean forAdmin
    );

}
