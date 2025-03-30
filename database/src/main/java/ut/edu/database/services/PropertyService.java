package ut.edu.database.services;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ut.edu.database.models.Property;
import ut.edu.database.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    //Constructor Injection
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Lấy tất cả bất động sản
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Lấy bất động sản theo ID
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    // Cập nhật bất động sản
    public Optional<Property> updateProperty(Long id, Property updatedProperty) {
        return propertyRepository.findById(id).map(existingProperty -> {
            existingProperty.setName(updatedProperty.getName());
            existingProperty.setLocation(updatedProperty.getLocation());
            existingProperty.setStatus(updatedProperty.getStatus());
            existingProperty.setPrice(updatedProperty.getPrice());
            existingProperty.setImage(updatedProperty.getImage());
            existingProperty.setDescription(updatedProperty.getDescription());
            return propertyRepository.save(existingProperty);
        });
    }

    //lay ds bat dong san thuoc 1 chu so huu
    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        if(ownerId == null || ownerId <= 0){
            throw new IllegalArgumentException("Invalid owner id");
        }
        return propertyRepository.findByOwnerId(ownerId);
    }

    //tim bat dong san theo vi tri
    public List<Property> getPropertiesByLocation(String location) {
        if(location == null || location.isBlank()){
            throw new IllegalArgumentException("Invalid location");
        } //cho phep tim chuoi con
        return propertyRepository.findByLocationContainingIgnoreCase(location);
    }
    //lay ds bat dong san theo status
    public List<Property> getPropertiesByStatus(String status) {
        try{
            Property.PropertyStatus propertyStatus = Property.PropertyStatus.valueOf(status.toUpperCase());
            return propertyRepository.findByStatus(propertyStatus.toString());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid property status: "+status);
        }
    }

    //tao bat dong san moi
    @Transactional  //dam bao giao dich db duoc thuc hien hoan chinh
                    //neu xay ra loi trong qua trinh save, moi thu se duoc rollback (tranh luu du lieu ko hop le)
    public Property createProperty(Property property) {
        if (property == null || property.getName() == null || property.getLocation() == null ||
                property.getPrice() == null || property.getImage() == null || property.getDescription() == null) {
            throw new IllegalArgumentException("Property and its required fields cannot be null");
        }
        return propertyRepository.save(property);
    }

    // Xóa bất động sản theo ID
    public boolean deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
