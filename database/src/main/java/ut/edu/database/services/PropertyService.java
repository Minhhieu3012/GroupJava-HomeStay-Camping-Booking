package ut.edu.database.services;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ut.edu.database.dtos.PropertyDTO;
import ut.edu.database.mapper.PropertyMapper;
import ut.edu.database.models.Property;
import ut.edu.database.enums.PropertyStatus;
import ut.edu.database.repositories.PropertyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {
    //Constructor Injection
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    // Lấy tất cả bất động sản
    public List<PropertyDTO> getAllPropertyDTOs() {
        return propertyRepository.findAll().stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Lấy bất động sản theo ID
    public Optional<PropertyDTO> getPropertyDTOById(Long id) {
        return propertyRepository.findById(id)
                .map(propertyMapper::toDTO);
    }

    // Dành cho nội bộ logic nghiệp vụ
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    // Cập nhật bất động sản
    public Optional<PropertyDTO> updateProperty(Long id, PropertyDTO updatedDTO) {
        return propertyRepository.findById(id).map(existing -> {
            existing.setName(updatedDTO.getName());
            existing.setLocation(updatedDTO.getLocation());
            existing.setPrice(updatedDTO.getPrice());
            existing.setDescription(updatedDTO.getDescription());
            existing.setImage(updatedDTO.getImage());
            existing.setStatus(updatedDTO.getStatus());

            return propertyMapper.toDTO(propertyRepository.save(existing));
        });
    }

    //tao bat dong san moi
    @Transactional  //dam bao giao dich db duoc thuc hien hoan chinh
                    //neu xay ra loi trong qua trinh save, moi thu se duoc rollback (tranh luu du lieu ko hop le)
    public PropertyDTO createPropertyDTO(PropertyDTO dto) {
        Property property = propertyMapper.toEntity(dto);
        property.setStatus(PropertyStatus.AVAILABLE); //trang thai mac dinh
        if (property.getName() == null || property.getLocation() == null || property.getPrice() == null || property.getImage() == null || property.getDescription() == null) {
            throw new IllegalArgumentException("Property and its required fields cannot be null");
        }
        return propertyMapper.toDTO(propertyRepository.save(property));

    }
    // Xóa (neu admin cho phep)
    public boolean deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //loc theo vi tri
    public List<PropertyDTO> filterByLocation(String location) {
        return propertyRepository.findByLocationContainingIgnoreCase(location).stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    //loc theo trang thai
    public List<PropertyDTO> filterByStatus(String status) {
        try{
            PropertyStatus enumStatus = PropertyStatus.valueOf(status.toUpperCase());
            return propertyRepository.findByStatus(enumStatus).stream()
                    .map(propertyMapper::toDTO)
                    .collect(Collectors.toList());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid status: "+status);
        }
    }

    public List<PropertyDTO> getPropertiesByOwnerEmail(String email) {
        return propertyRepository.findByOwnerEmail(email)
                .stream()
                .map(propertyMapper::toDTO)
                .toList();
    }

}
