//package admin.bookinghomecamping.controller;
//
//import admin.bookinghomecamping.dto.TransactionDTO;
//import admin.bookinghomecamping.dto.UserDTO;
//import org.springframework.ui.Model;
////import ch.qos.logback.core.model.Model;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminPageController {
//
//    @GetMapping("/revenue-report")
//    public String showRevenueReportPage() {
//        return "bookingHomeCamping/BaoCaoDoanhThu"; // Trỏ đến file templates/admin/revenue_report.html
//    }
//    @GetMapping("/service-fee-report")
//    public String showServiceFeeReportPage(Model model) {
//        ArrayList<TransactionDTO> transactions = new ArrayList<>();
//        transactions.add(new TransactionDTO("GD001", "Ngô Gia Cò", 1000000));
//        transactions.add(new TransactionDTO("GD002", "Phan Minh MU", 2000000));
//        transactions.add(new TransactionDTO("GD003", "Lê Ngô Hữu Kền", 1500000));
//
//        model.addAttribute("transactions", transactions);
//        return "bookingHomeCamping/QuanLiPhiDichVu";
//    }
//}
