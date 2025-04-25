package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.multipart.MultipartFile;
import ut.edu.database.dtos.PropertyDTO;
import ut.edu.database.models.Property;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;
import ut.edu.database.services.PropertyService;
import ut.edu.database.services.UserService;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/upload-img/{propertyId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> uploadImg(@PathVariable Long propertyId, @RequestParam("file") MultipartFile file) {
        try{
            Property property = propertyService.getPropertyById(propertyId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Property!!!"));
            //1 tao ten file duy nhat
            String filename = UUID.randomUUID()+"_"+file.getOriginalFilename();

            //2 duong dan luu anh (resources/static/properties)
            String uploadDir = new ClassPathResource("static/properties").getFile().getAbsolutePath();
            File saveFile = new File(uploadDir + File.separator + filename);
            file.transferTo(saveFile); //luu file anh

            //3 luu url vao db
            String imageUrl = "/static/properties/"+filename;
            property.setImage(imageUrl);
            propertyService.save(property); //co the goi lai repo.save()

            return ResponseEntity.ok("Tải lên thành công! URL: "+imageUrl);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Tải lên thất bại :((: "+e.getMessage());
        }
    }
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
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
        User owner = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User :(("));
        return ResponseEntity.ok(propertyService.createPropertyDTO(dto, owner));
    }

    //PUT: cap nhat - chi owner
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PropertyDTO> update(@PathVariable Long id, @RequestBody PropertyDTO dto, @AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null); // Or create a custom error DTO
        }
        PropertyDTO existing = propertyService.getPropertyDTOById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Property :(("));

        Long currentUserId = userService.getUserIdByUsername(user.getUsername());
        if (!existing.getOwner_id().equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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

    // Lấy danh sách property của chính Owner đang đăng nhập
    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<PropertyDTO>> getMyProperties(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User :(("));

        return ResponseEntity.ok(propertyService.getPropertiesByOwnerEmail(user.getEmail()));
    }
}
