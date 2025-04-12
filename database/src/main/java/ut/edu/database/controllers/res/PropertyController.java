package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import ut.edu.database.dtos.PropertyDTO;
import ut.edu.database.services.PropertyService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    //GET: lay tat ca bat dong san
    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllPropertyDTOs()); //200 ok
    }

    //GET: lay bat dong san theo id
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getById(@PathVariable Long id) {
        Optional<PropertyDTO> property = propertyService.getPropertyDTOById(id);
        return property.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST: tao moi - chi owner
    @PostMapping("/create")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO dto) {
        return ResponseEntity.ok(propertyService.createPropertyDTO(dto));
    }

    //PUT: cap nhat - chi owner
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PropertyDTO> update(@PathVariable Long id, @RequestBody PropertyDTO dto) {
        Optional<PropertyDTO> updated = propertyService.updateProperty(id,dto);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //DELETE: xoa - chi admin
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(propertyService.deleteProperty(id)) {
            return ResponseEntity.noContent().build(); //204 no content khi xoa thanh cong
        }
        return ResponseEntity.notFound().build();// 404 khong tim thay
    }

    //loc theo vi tri
    @GetMapping("/search")
    public ResponseEntity<List<PropertyDTO>> filterByLocation(@RequestParam String location) {
        return ResponseEntity.ok(propertyService.filterByLocation(location));
    }

    //loc theo trang thai
    @GetMapping("/status")
    public ResponseEntity<List<PropertyDTO>> filterByStatus(@RequestParam String status) {
        return ResponseEntity.ok(propertyService.filterByStatus(status));
    }
}
