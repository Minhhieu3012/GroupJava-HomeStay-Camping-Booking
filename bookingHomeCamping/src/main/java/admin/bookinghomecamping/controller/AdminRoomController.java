////MOCK DATA
//
//package admin.bookinghomecamping.controller;
//
//import admin.bookinghomecamping.dto.RoomDTO;
//import admin.bookinghomecamping.entity.Room;
//import admin.bookinghomecamping.repository.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminRoomController {
//
//    @GetMapping("/add-room")
//    public String showAddRoomForm(Model model) {
//        model.addAttribute("room", new RoomDTO(
//                "", "", 0.0, "", "", "ACTIVE", 1L
//        )); // khởi tạo giá trị trống để binding form
//        return "bookingHomeCamping/ThemPhong"; // Tên file HTML
//    }
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @GetMapping("/delete-room")
//    public String showDeleteRoomPage(Model model) {
//        List<Room> roomList = roomRepository.findAll();
//        model.addAttribute("roomList", roomList);
//        return "bookingHomeCamping/XoaPhong";
//    }
//
//    @PostMapping("/delete-room/{id}")
//    public String deleteRoom(@PathVariable Long id) {
//        roomRepository.deleteById(id);
//        return "redirect:/admin/delete-room";
//    }
//
////MOCK DATA
//
////    @Controller
////    @RequestMapping("/admin")
////    public class RoomController {
////
////        private List<Room> mockRoomList = new ArrayList<>();
////
////        public RoomController() {
////            // Khởi tạo mock data
////            mockRoomList.add(new Room(1L, "Phòng Luxury", "Bungalow", 1500000.0, "Đà Lạt"));
////            mockRoomList.add(new Room(2L, "Phòng Gác Mái", "Tent", 800000.0, "Vũng Tàu"));
////            mockRoomList.add(new Room(3L, "Phòng View Núi", "Cabin", 1200000.0, "Sapa"));
////        }
////
////        @GetMapping("/delete-room")
////        public String showDeleteRoomPage(Model model) {
////            model.addAttribute("roomList", mockRoomList);
////            return "bookingHomeCamping/XoaPhong";
////        }
////
////        @PostMapping("/delete-room/{id}")
////        public String deleteRoom(@PathVariable Long id) {
////            mockRoomList.removeIf(room -> room.getId().equals(id));
////            return "redirect:/admin/delete-room";
////        }
////    }
//}
