package admin.bookinghomecamping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/revenue-report")
    public String showRevenueReportPage() {
        return "bookingHomeCamping/BaoCaoDoanhThu"; // Trỏ đến file templates/admin/revenue_report.html
    }
}
