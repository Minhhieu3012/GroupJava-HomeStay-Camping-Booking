package ut.edu.database.services;
import ut.edu.database.models.Property;
import ut.edu.database.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    //
    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
    //
    public List<Property> getPropertiesByLocation(String location) {
        return propertyRepository.findByLocationContainingIgnoreCase(location);
    }
    //
    public List<Property> getPropertiesByStatus(String status) {
        return propertyRepository.findByStatus(status);
    }
    //
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }
}
