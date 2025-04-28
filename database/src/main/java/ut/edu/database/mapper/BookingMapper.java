package ut.edu.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ut.edu.database.dtos.BookingDTO;
import ut.edu.database.models.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    //ENTITY --> DTO
    @Mapping(source = "user.id", target = "userID")
    @Mapping(source = "user.username", target = "username") // thêm dòng này
    @Mapping(source = "property.id", target = "propertyID")
    @Mapping(source = "property.name", target = "propertyName") // thêm dòng này
    @Mapping(source = "adminFee", target = "adminFee")
    @Mapping(source = "ownerEarning", target = "ownerEarning")
    BookingDTO toDTO (Booking booking);

    //DTO --> ENTITY
    @Mapping(source = "userID", target = "user.id") //can set thu cong o service
    @Mapping(source = "propertyID", target = "property.id")
    @Mapping(source = "adminFee", target = "adminFee")
    @Mapping(source = "ownerEarning", target = "ownerEarning")
    Booking toEntity(BookingDTO dto);

}
