package propertyregistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import propertyregistration.models.Homestay;
import propertyregistration.service.PropertyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // Đăng ký homestay/camping
    @PostMapping("/register")
    public ResponseEntity<Homestay> registerHomestay(@RequestBody Homestay homestay) {
        Homestay newHomestay = propertyService.registerHomestay(homestay);
        return ResponseEntity.ok(newHomestay);
    }

    // Cập nhật thông tin homestay/camping
    @PutMapping("/update/{id}")
    public ResponseEntity<Homestay> updateHomestay(@PathVariable Long id, @RequestBody Homestay homestay) {
        Homestay updatedHomestay = propertyService.updateHomestay(id, homestay);
        return updatedHomestay != null ? ResponseEntity.ok(updatedHomestay) : ResponseEntity.notFound().build();
    }

    // Lấy thông tin chi tiết một bất động sản
    @GetMapping("/{id}")
    public ResponseEntity<Homestay> getHomestayById(@PathVariable Long id) {
        Optional<Homestay> homestay = propertyService.getHomestayById(id);
        return homestay.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lấy danh sách tất cả bất động sản
    @GetMapping("/all")
    public ResponseEntity<List<Homestay>> getAllHomestays() {
        return ResponseEntity.ok(propertyService.getAllHomestays());
    }

    // Xóa bất động sản
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHomestay(@PathVariable Long id) {
        propertyService.deleteHomestay(id);
        return ResponseEntity.noContent().build();
    }
}
