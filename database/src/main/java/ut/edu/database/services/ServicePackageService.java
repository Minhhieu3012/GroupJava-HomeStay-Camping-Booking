package ut.edu.database.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.database.dtos.ServicePackageDTO;
import ut.edu.database.enums.TimeSlot;
import ut.edu.database.models.Property;
import ut.edu.database.models.ServicePackage;
import ut.edu.database.repositories.PropertyRepository;
import ut.edu.database.repositories.ServicePackageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicePackageService {
    private final ServicePackageRepository servicePackageRepository;
    private final PropertyRepository propertyRepository;

    public ServicePackageDTO create(ServicePackageDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Property :(("));
        ServicePackage sp = new ServicePackage();
        sp.setName(dto.getName());
        sp.setDescription(dto.getDescription());
        sp.setPrice(dto.getPrice());
        sp.setTimeSlot(TimeSlot.valueOf(dto.getTimeSlot().toUpperCase()));
        sp.setProperty(property);
        sp.setAvailable(true);

        servicePackageRepository.save(sp);
        dto.setId(sp.getId());
        return dto;
    }

    public List<ServicePackageDTO> getAllByProperty(Long propertyId) {
        return servicePackageRepository.findByPropertyIdAndAvailableTrue(propertyId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ServicePackageDTO mapToDTO(ServicePackage sp) {
        ServicePackageDTO dto = new ServicePackageDTO();
        dto.setId(sp.getId());
        dto.setName(sp.getName());
        dto.setDescription(sp.getDescription());
        dto.setPrice(sp.getPrice());
        dto.setTimeSlot(sp.getTimeSlot().toString());
        dto.setPropertyId(sp.getProperty().getId());
        return dto;
    }
}
