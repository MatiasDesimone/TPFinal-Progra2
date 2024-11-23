package org.example;


public class Main {
    public static void main(String[] args) {


    }
}

//TODO: , , , quiza compras por puntos, comprobantes, JSON.

//<dependency>
//    <groupId>org.xhtmlrenderer</groupId>
//    <artifactId>flying-saucer-pdf</artifactId>
//    <version>9.1.22</version>
//</dependency>
//<dependency>
//    <groupId>com.itextpdf</groupId>
//    <artifactId>itextpdf</artifactId>
//    <version>5.5.13.2</version>
//</dependency>




//<!DOCTYPE html>
//<html>
//<head>
//    <style>
//        body {
//            font-family: Arial, sans-serif;
//        }
//        .header {
//            text-align: center;
//            margin-bottom: 20px;
//        }
//        .content {
//            margin: 20px;
//        }
//        .footer {
//            text-align: center;
//            margin-top: 20px;
//        }
//    </style>
//</head>
//<body>
//    <div class="header">
//        <h1>Transaction Receipt</h1>
//    </div>
//    <div class="content">
//        <p><strong>Transaction ID:</strong> {{transactionId}}</p>
//        <p><strong>Date:</strong> {{date}}</p>
//        <p><strong>Amount:</strong> {{amount}}</p>
//        <p><strong>Description:</strong> {{description}}</p>
//    </div>
//    <div class="footer">
//        <p>Thank you for your business!</p>
//    </div>
//</body>
//</html>



//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import java.io.FileOutputStream;
//import java.io.StringReader;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Map;
//
//public class PdfGenerator {
//
//    public static void generatePdf(String htmlTemplatePath, Map<String, String> data, String outputPdfPath) throws Exception {
//        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlTemplatePath)));
//
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
//        }
//
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPdfPath));
//        document.open();
//
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(htmlContent);
//        renderer.layout();
//        renderer.createPDF(writer.getDirectContent().createGraphicsShapes(595, 842));
//
//        document.close();
//    }
//}



//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        List<Transaction> transactions = new ArrayList<>();
//        // Add transactions to the list
//        transactions.add(new Transaction("123456", "2023-10-01", "$100.00", "Payment for services"));
//        transactions.add(new Transaction("123457", "2023-10-02", "$200.00", "Payment for products"));
//
//        for (Transaction transaction : transactions) {
//            try {
//                Map<String, String> data = new HashMap<>();
//                data.put("transactionId", transaction.getTransactionId());
//                data.put("date", transaction.getDate());
//                data.put("amount", transaction.getAmount());
//                data.put("description", transaction.getDescription());
//
//                PdfGenerator.generatePdf("path/to/receipt_template.html", data, "path/to/output_receipt_" + transaction.getTransactionId() + ".pdf");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}