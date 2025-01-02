package repository.order;

import java.util.List;

public interface OrderRepository {

    List<String> getSalesReport(String startDate, String endDate);
}