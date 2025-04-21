//KẾT NỐI DB ĐƯA DỮ LIỆU THÂT

//package admin.bookinghomecamping.controller;
//
//import admin.bookinghomecamping.entity.Room;
//import admin.bookinghomecamping.repository.RoomRepository;
//import org.springframework.ui.Model;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class RoomController {
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @GetMapping("/add-room")
//    public String showAddRoomForm(Model model) {
//        model.addAttribute("room", new Room());
//        return "bookingHomeCamping/ThemPhong";
//    }
//
//    @PostMapping("/add-room")
//    public String addRoom(@ModelAttribute Room room) {
//        roomRepository.save(room);
//        return "redirect:/rooms"; // Redirect tới trang hiển thị danh sách phòng
//    }
//
//    @GetMapping("/rooms")
//    public String listRooms(Model model) {
//        List<Room> rooms = roomRepository.findAll();
//        model.addAttribute("rooms", rooms);
//        return "bookingHomeCamping/DanhSachPhong"; // tạo thêm file HTML danh sách phòng
//    }
//}
