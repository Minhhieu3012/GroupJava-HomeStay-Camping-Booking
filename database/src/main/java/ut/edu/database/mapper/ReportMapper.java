package ut.edu.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ut.edu.database.models.Report;
import ut.edu.database.dtos.ReportDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(source = "property.id", target = "propertyID")
    ReportDTO toDTO(Report report);

    @Mapping(target = "property", ignore = true)
    Report toEntity(ReportDTO dto);

}
