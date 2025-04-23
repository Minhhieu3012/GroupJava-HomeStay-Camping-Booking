package ut.edu.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ut.edu.database.models.Payment;
import ut.edu.database.dtos.PaymentDTO;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    //ENTITY --> DTO
    @Mapping(source = "booking.id", target = "bookingID")
    PaymentDTO toDTO (Payment payment);

    //DTO --> ENTITY
    @Mapping(source = "bookingID", target = "booking.id")
    Payment toEntity(PaymentDTO dto);
}
