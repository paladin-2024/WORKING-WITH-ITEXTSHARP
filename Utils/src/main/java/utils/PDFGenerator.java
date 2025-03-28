package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import java.io.FileOutputStream;

public class PDFGenerator {
    public static void generatePDF(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (Document document = new Document()) {
                PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
                document.open();

                // Title
                document.add(new Paragraph("Employee Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                document.add(new Paragraph("Generated on: " + java.time.LocalDate.now()));
                document.add(new Paragraph(" ")); // Add space

                // Table
                PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
                TableModel model = table.getModel();

                // Add column headers
                for (int col = 0; col < model.getColumnCount(); col++) {
                    pdfTable.addCell(new PdfPCell(new Phrase(model.getColumnName(col))));
                }

                // Add rows
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        pdfTable.addCell(new PdfPCell(new Phrase(model.getValueAt(row, col).toString())));
                    }
                }

                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(null, "PDF exported successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
