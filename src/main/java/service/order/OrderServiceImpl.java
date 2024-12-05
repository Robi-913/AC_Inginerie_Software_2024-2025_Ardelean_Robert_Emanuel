package service.order;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import repository.order.OrderRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void generateReport(String startDate, String endDate, String outputFilePath) {
        List<String> reportData = orderRepository.getSalesReport(startDate, endDate);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
            document.open();

            document.add(new Paragraph("Sales Report"));
            document.add(new Paragraph("Period: " + startDate + " to " + endDate));
            document.add(new Paragraph("-----------------------------------------------------------"));
            document.add(new Paragraph("Details:"));
            document.add(new Paragraph("\n"));

            for (String entry : reportData) {
                document.add(new Paragraph(entry));
                document.add(new Paragraph("-----------------------------------------------------------"));
            }

            document.add(new Paragraph("\nReport generated successfully."));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
