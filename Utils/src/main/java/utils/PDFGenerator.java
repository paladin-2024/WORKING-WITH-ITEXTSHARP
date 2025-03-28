package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class PDFGenerator {
    public static void generatePDF(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
                document.open();

                Paragraph title = new Paragraph("Employee Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                Paragraph date = new Paragraph("Generated on: " + LocalDate.now());
                date.setAlignment(Element.ALIGN_CENTER);
                document.add(date);

                document.add(new Paragraph(" "));

                PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
                TableModel model = table.getModel();

                for (int col = 0; col < model.getColumnCount(); col++) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(model.getColumnName(col)));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    pdfTable.addCell(headerCell);
                }

                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        PdfPCell cell = new PdfPCell(new Phrase(model.getValueAt(row, col).toString()));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfTable.addCell(cell);
                    }
                }

                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(null, "PDF exported successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occurred while exporting the PDF: " + e.getMessage(),
                        "Export Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
