package admin.bookinghomecamping.dto;

//MOCK DỮ LIỆU

public class RevenueReportDTO {
    private int month;
    private double totalRevenue;

    public RevenueReportDTO(int month, double totalRevenue) {
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public int getMonth() { return month; }
    public double getTotalRevenue() { return totalRevenue; }
}

//DỮ LIỆU THẬT BAOCAODOANHTHU

//public class RevenueReportDTO {
//    private int month;
//    private double totalRevenue;
//
//    public RevenueReportDTO(int month, double totalRevenue) {
//        this.month = month;
//        this.totalRevenue = totalRevenue;
//    }
//
//    // getters & setters
//}
