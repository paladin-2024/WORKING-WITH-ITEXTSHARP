package util;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDFExporter {
    private static PdfFont titleFont;
    private static PdfFont normalFont;

    static {
        try {
            titleFont = PdfFontFactory.createFont("Helvetica-Bold");
            normalFont = PdfFontFactory.createFont("Helvetica");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error initializing fonts: " + e.getMessage(),
                    "Font Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void exportTableToPDF(JTable table, String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        if (!filePath.toLowerCase().endsWith(".pdf")) {
            filePath += ".pdf";
        }

        try (PdfWriter writer = new PdfWriter(filePath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            Paragraph header = new Paragraph(title)
                    .setFont(titleFont)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            document.add(header);

            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
            Paragraph date = new Paragraph("Report Date: " + formattedDate)
                    .setFont(normalFont)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(date);

            TableModel model = table.getModel();
            float[] columnWidths = new float[model.getColumnCount()];
            for (int i = 0; i < columnWidths.length; i++) {
                columnWidths[i] = 1;
            }

            Table pdfTable = new Table(columnWidths);
            pdfTable.setWidth(100);

            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell headerCell = new Cell()
                        .add(new Paragraph(model.getColumnName(i)))
                        .setFont(titleFont)
                        .setFontSize(12)
                        .setBold()
                        .setBackgroundColor(new DeviceRgb(211, 211, 211)) // Light Gray Background
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE);
                pdfTable.addHeaderCell(headerCell);
            }

            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    Cell cell = new Cell()
                            .add(new Paragraph(value != null ? value.toString() : ""))
                            .setFont(normalFont)
                            .setFontSize(10)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE);
                    pdfTable.addCell(cell);
                }
            }

            document.add(pdfTable);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error exporting PDF: " + e.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
