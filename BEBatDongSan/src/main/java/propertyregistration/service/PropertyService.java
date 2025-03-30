package propertyregistration.service;

import propertyregistration.models.Homestay;
import java.util.List;
import java.util.Optional;

public interface PropertyService {
    Homestay registerHomestay(Homestay homestay);
    Homestay updateHomestay(Long id, Homestay homestay);
    Optional<Homestay> getHomestayById(Long id);
    List<Homestay> getAllHomestays();
    void deleteHomestay(Long id);
}