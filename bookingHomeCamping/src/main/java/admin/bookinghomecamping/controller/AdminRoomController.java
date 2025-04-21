//MOCK DATA

package admin.bookinghomecamping.controller;

import admin.bookinghomecamping.dto.RoomDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminRoomController {

    @GetMapping("/add-room")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new RoomDTO(
                "", "", 0.0, "", "", "ACTIVE", 1L
        )); // khởi tạo giá trị trống để binding form
        return "bookingHomeCamping/ThemPhong"; // Tên file HTML
    }
}
