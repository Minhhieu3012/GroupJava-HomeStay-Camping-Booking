package ut.edu.database.services;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ut.edu.database.dtos.PropertyDTO;
import ut.edu.database.mapper.PropertyMapper;
import ut.edu.database.models.Property;
import ut.edu.database.enums.PropertyStatus;
import ut.edu.database.models.User;
import ut.edu.database.repositories.PropertyRepository;
import ut.edu.database.repositories.UserRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {
    //Constructor Injection
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserRepository userRepository;
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
    public PropertyDTO createPropertyDTO(PropertyDTO dto, User userDetail) {
        //Kiểm tra owner tồn tại
        User owner = userRepository.findByUsername(userDetail.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy chủ sở hữu với id: " + dto.getOwner_id()));

        Property property = propertyMapper.toEntity(dto);
        property.setOwner(owner);
        property.setStatus(PropertyStatus.AVAILABLE); //trang thai mac dinh
        if (property.getName() == null || property.getLocation() == null || property.getPrice() == null || property.getImage() == null || property.getDescription() == null) {
            throw new IllegalArgumentException("Thuộc tính và các trường bắt buộc của nó không thể là null -.-");
        }
        Property saved = propertyRepository.save(property);
        propertyRepository.save(property);
        return propertyMapper.toDTO(saved);

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
            throw new IllegalArgumentException("Trạng thái không hợp lệ: "+status);
        }
    }

    public List<PropertyDTO> getPropertiesByOwnerEmail(String email) {
        return propertyRepository.findByOwnerEmail(email)
                .stream()
                .map(propertyMapper::toDTO)
                .toList();
    }

    public Property save(Property property) {
        return propertyRepository.save(property);
    }


    public void updatePropertyWithImage(PropertyDTO dto, MultipartFile imageFile) {
        Optional<Property> optional = propertyRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Property property = optional.get();

            property.setName(dto.getName());
            property.setLocation(dto.getLocation());
            property.setPrice(dto.getPrice());
            property.setDescription(dto.getDescription());
            property.setStatus(dto.getStatus());

            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                    String uploadDir = new ClassPathResource("static/properties").getFile().getAbsolutePath();
                    File saveFile = new File(uploadDir + File.separator + filename);
                    imageFile.transferTo(saveFile);

                    //Đường dẫn LƯU trong DB phải bỏ "/static"
                    property.setImage("/properties/" + filename);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            propertyRepository.save(property);
        }
    }

    //roll back neu co loi trong qua trinh save
    @Transactional
    public void save(PropertyDTO propertyDTO) {
        // Convert DTO thành Entity
        Property property = propertyMapper.toEntity(propertyDTO);

        // Gán Owner cho Property
        if (propertyDTO.getOwner_id() != null) {
            Optional<User> ownerOpt = userRepository.findById(propertyDTO.getOwner_id());
            if (ownerOpt.isPresent()) {
                property.setOwner(ownerOpt.get());
            } else {
                throw new IllegalArgumentException("Không tìm thấy chủ phòng với ID: " + propertyDTO.getOwner_id());
            }
        } else {
            throw new IllegalArgumentException("Thiếu Owner ID");
        }

        // Set trạng thái mặc định nếu chưa có
        if (property.getStatus() == null) {
            property.setStatus(PropertyStatus.AVAILABLE);
        }

        // Save vào database
        propertyRepository.save(property);
    }
}
