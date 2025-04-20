package admin.bookinghomecamping.dto;

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
