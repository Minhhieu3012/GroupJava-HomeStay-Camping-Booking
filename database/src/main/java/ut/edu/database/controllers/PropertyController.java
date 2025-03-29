package ut.edu.database.controllers;
import ut.edu.database.models.Property;
import ut.edu.database.repositories.PropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyRepository propertyRepository;

    //lay tat ca bat dong san
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    //lay bat dong san theo id
    @GetMapping("/{id}")
    public Optional<Property> getPropertyById(@PathVariable Long id) {
        return propertyRepository.findById(id);
    }
    //tao bat dong san moi
    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyRepository.save(property);
    }
    //update thong tin bat dong san
    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Long id, @RequestBody Property property) {
        return propertyRepository.save(property);
    }
    //delete bat dong san
    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id) {
        propertyRepository.deleteById(id);
    }
}
