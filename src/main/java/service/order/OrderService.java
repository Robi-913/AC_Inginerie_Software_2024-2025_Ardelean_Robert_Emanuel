package service.order;

public interface OrderService {
    void generateReport(String startDate, String endDate, String outputFilePath);
}