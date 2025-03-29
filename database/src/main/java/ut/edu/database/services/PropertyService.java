package ut.edu.database.services;
import jakarta.transaction.Transactional;
import ut.edu.database.models.Property;
import ut.edu.database.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    //lay ds bat dong san thuoc 1 chu so huu
    public List<Property> getPropertiesByOwnerId(Long ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
    //tim bat dong san theo vi tri
    public List<Property> getPropertiesByLocation(String location) {
        return propertyRepository.findByLocationContainingIgnoreCase(location); //cho phep tim chuoi con
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

    @Transactional  //dam bao giao dich db duoc thuc hien hoan chinh
                    //neu xay ra loi trong qua trinh save, moi thu se duoc rollback (tranh luu du lieu ko hop le)
    public Property createProperty(Property property) {
        if(property == null || property.getName() == null || property.getLocation() == null){ //tranh luu obj rong
            throw new IllegalArgumentException("Property cannot be null");
        }
        return propertyRepository.save(property);
    }
}
