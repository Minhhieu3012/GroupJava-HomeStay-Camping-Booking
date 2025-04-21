package ut.edu.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ut.edu.database.dtos.BookingDTO;
import ut.edu.database.models.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    //ENTITY --> DTO
    @Mapping(source = "user.id", target = "userID")
    @Mapping(source = "property.id", target = "propertyID")
    BookingDTO toDTO (Booking booking);

    //DTO --> ENTITY
    @Mapping(source = "userID", target = "user.id") //can set thu cong o service
    @Mapping(source = "propertyID", target = "property.id")
    Booking toEntity(BookingDTO dto);

}
