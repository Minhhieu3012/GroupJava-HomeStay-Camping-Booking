package ut.edu.database.mapper;

import ut.edu.database.models.Review;
import ut.edu.database.dtos.ReviewDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "property.id",target = "propertyID")
    @Mapping(source = "user.id", target = "userID")
    ReviewDTO toDTO(Review review);

    @Mapping(target = "property", ignore = true)
    @Mapping(target = "user", ignore = true)
    Review toEntity(ReviewDTO dto);
}
