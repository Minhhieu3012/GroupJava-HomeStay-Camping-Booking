package ut.edu.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ut.edu.database.models.Property;
import ut.edu.database.dtos.PropertyDTO;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(source = "owner.id", target = "owner_id")
    PropertyDTO toDTO(Property property);

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "reports", ignore = true)
    Property toEntity(PropertyDTO dto);
}
