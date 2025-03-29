package ut.edu.database.controllers;
import ut.edu.database.models.Property;
import ut.edu.database.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    private final PropertyService propertyService;

    //constructor injection
    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    //lay tat ca bat dong san
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties); //200 ok
    }

    //lay bat dong san theo id
    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //tao bat dong san moi
    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property savedProperty = propertyService.createProperty(property);
        return ResponseEntity.ok(savedProperty); //200 ok
    }

    //update thong tin bat dong san theo id
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property updatedProperty) {
        return propertyService.updateProperty(id,updatedProperty)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //delete bat dong san
    @DeleteMapping("/{id}")
    public ResponseEntity<Property> deleteProperty(@PathVariable Long id) {
        if(propertyService.deleteProperty(id)) {
            return ResponseEntity.noContent().build(); //204 no content khi xoa thanh cong
        }
        return ResponseEntity.notFound().build();// 404 khong tim thay
    }
}
