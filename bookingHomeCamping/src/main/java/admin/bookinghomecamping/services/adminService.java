package admin.bookinghomecamping.services;
import admin.bookinghomecamping.models.admin;
import admin.bookinghomecamping.repositories.adminRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
@Service
public class adminService {
    @Autowired
    private adminRepository adminRepository;
    public Optional<admin> findByadminName(String adminName) {
        return adminRepository.findByAdminName(adminName);
    }
    public List<admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    //Create admin
    public long CreateAdmin(admin admin) {
        return adminRepository.save(admin).getId();
    }
    // Save hoặc update Admin
   public admin updateAdmin(admin admin) {
        return adminRepository.save(admin);
   }

    // Xóa Admin theo ID
    public void deleteAdmin(Long id) {
        Optional<admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            adminRepository.deleteById(id);
        } else {
            throw new RuntimeException("Admin không tồn tại!");
        }
    }
}
