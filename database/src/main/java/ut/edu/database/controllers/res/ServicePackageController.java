package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServicePackageDTO> create(@RequestBody ServicePackageDTO dto) {
        return ResponseEntity.ok(servicePackageService.create(dto));
    }

    @GetMapping("/property/{propertyId}")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public ResponseEntity<List<ServicePackageDTO>> getByProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(servicePackageService.getAllByProperty(propertyId));
    }
}
