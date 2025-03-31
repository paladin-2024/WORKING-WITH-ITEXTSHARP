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
import com.itextpdf.layout.properties.HorizontalAlignment;
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
        initializeFonts();
    }

    private static void initializeFonts() {
        try {
            titleFont = PdfFontFactory.createFont("Helvetica-Bold");
            normalFont = PdfFontFactory.createFont("Helvetica");
        } catch (IOException e) {
            showFontError(e);
        }
    }

    public static void exportTableToPDF(JTable table, String title) {
        File file = getSaveFile();
        if (file == null) return;

        try (PdfWriter writer = new PdfWriter(file);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            addDocumentHeader(document, title);
            Table pdfTable = createPdfTable(table.getModel());
            document.add(pdfTable);

        } catch (Exception e) {
            showExportError(e);
        }
    }

    private static File getSaveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return null;

        File file = fileChooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".pdf")) {
            return new File(file.getAbsolutePath() + ".pdf");
        }
        return file;
    }

    private static void addDocumentHeader(Document doc, String title) {
        doc.add(createTitleParagraph(title));
        doc.add(createDateParagraph());
    }

    private static Paragraph createTitleParagraph(String title) {
        return new Paragraph(title)
                .setFont(titleFont)
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
    }

    private static Paragraph createDateParagraph() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        return new Paragraph("Generated: " + date)
                .setFont(normalFont)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
    }

    private static Table createPdfTable(TableModel model) {
        Table table = new Table(model.getColumnCount());
        configureTableAppearance(table);
        addTableHeaders(table, model);
        addTableRows(table, model);
        return table;
    }

    private static void configureTableAppearance(Table table) {
        table.setWidth(100)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMarginTop(10)
                .setMarginBottom(10);
    }

    private static void addTableHeaders(Table table, TableModel model) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            table.addHeaderCell(createHeaderCell(model.getColumnName(i)));
        }
    }

    private static Cell createHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setFont(titleFont)
                .setFontSize(12)
                .setBold()
                .setBackgroundColor(new DeviceRgb(211, 211, 211))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    private static void addTableRows(Table table, TableModel model) {
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                table.addCell(createDataCell(model.getValueAt(row, col)));
            }
        }
    }

    private static Cell createDataCell(Object value) {
        return new Cell()
                .add(new Paragraph(value != null ? value.toString() : ""))
                .setFont(normalFont)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    private static void showFontError(IOException e) {
        JOptionPane.showMessageDialog(null,
                "Font initialization failed: " + e.getMessage(),
                "PDF Export Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private static void showExportError(Exception e) {
        JOptionPane.showMessageDialog(null,
                "PDF Export Failed: " + e.getMessage(),
                "Export Error",
                JOptionPane.ERROR_MESSAGE);
    }
}