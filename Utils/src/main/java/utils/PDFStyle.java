package utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import java.io.IOException;

public class PDFStyle {
    private static PdfFont titleFont;
    private static PdfFont normalFont;
    private static Style headerStyle;
    private static Style cellStyle;

    static {
        try {
            // Register fonts (you might need to include font files in resources)
            titleFont = PdfFontFactory.createFont("Helvetica-Bold", PdfEncodings.WINANSI);
            normalFont = PdfFontFactory.createFont("Helvetica", PdfEncodings.WINANSI);

            // Header style
            headerStyle = new Style()
                    .setFont(normalFont)
                    .setFontSize(12)
                    .setBold()
                    .setBackgroundColor(new DeviceRgb(200, 200, 200))
                    .setPadding(5);

            // Cell style
            cellStyle = new Style()
                    .setFont(normalFont)
                    .setFontSize(10)
                    .setPadding(5);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PdfFont getTitleFont() {
        return titleFont;
    }

    public static PdfFont getNormalFont() {
        return normalFont;
    }

    public static Style getHeaderStyle() {
        return headerStyle;
    }

    public static Style getCellStyle() {
        return cellStyle;
    }
}