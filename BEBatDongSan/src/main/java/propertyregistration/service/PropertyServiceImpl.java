package propertyregistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propertyregistration.models.Homestay;
import propertyregistration.repository.HomestayRepository;
import propertyregistration.service.PropertyService;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private HomestayRepository homestayRepository;

    @Override
    public Homestay registerHomestay(Homestay homestay) {
        return homestayRepository.save(homestay);
    }

    @Override
    public Homestay updateHomestay(Long id, Homestay homestay) {
        Optional<Homestay> existingHomestay = homestayRepository.findById(id);
        if (existingHomestay.isPresent()) {
            Homestay updatedHomestay = existingHomestay.get();
            updatedHomestay.setName(homestay.getName());
            updatedHomestay.setLocation(homestay.getLocation());
            updatedHomestay.setPrice(homestay.getPrice());
            updatedHomestay.setRooms(homestay.getRooms());
            updatedHomestay.setAmenities(homestay.getAmenities());
            updatedHomestay.setDescription(homestay.getDescription());
            return homestayRepository.save(updatedHomestay);
        }
        return null;
    }

    @Override
    public Optional<Homestay> getHomestayById(Long id) {
        return homestayRepository.findById(id);
    }

    @Override
    public List<Homestay> getAllHomestays() {
        return homestayRepository.findAll();
    }

    @Override
    public void deleteHomestay(Long id) {
        homestayRepository.deleteById(id);
    }
}
