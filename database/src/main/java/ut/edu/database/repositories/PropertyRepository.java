package ut.edu.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.database.enums.PropertyStatus;
import ut.edu.database.models.Property;
import ut.edu.database.models.User;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Tìm kiếm theo địa điểm (không phân biệt hoa thường)
    List<Property> findByLocationContainingIgnoreCase(String location);

    // Lọc theo chủ sở hữu
    List<Property> findByOwner(User owner);

    // Tìm kiếm theo trạng thái (AVAILABLE/BOOKED)
    List<Property> findByStatus(PropertyStatus status);

}