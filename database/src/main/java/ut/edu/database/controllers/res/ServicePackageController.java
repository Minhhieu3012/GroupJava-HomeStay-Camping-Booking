package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.database.dtos.ServicePackageDTO;
import ut.edu.database.services.ServicePackageService;

import java.util.List;

@RestController
@RequestMapping("/api/service-packages")
@RequiredArgsConstructor
public class ServicePackageController {
    private final ServicePackageService servicePackageService;

    @PostMapping
    public ResponseEntity<ServicePackageDTO> create(@RequestBody ServicePackageDTO dto) {
        return ResponseEntity.ok(servicePackageService.create(dto));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ServicePackageDTO>> getByProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(servicePackageService.getAllByProperty(propertyId));
    }
}
