package utils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.IOException;

public class PDFStyle {
    private static PdfFont titleFont;
    private static PdfFont normalFont;
    private static Style headerStyle;
    private static Style cellStyle;

    static {
        try {
            titleFont = PdfFontFactory.createFont("Helvetica-Bold", PdfEncodings.WINANSI);
            normalFont = PdfFontFactory.createFont("Helvetica", PdfEncodings.WINANSI);

            headerStyle = new Style()
                    .setFont(titleFont)
                    .setFontSize(12)
                    .setBackgroundColor(new DeviceRgb(220, 220, 220))
                    .setPadding(5)
                    .setTextAlignment(TextAlignment.CENTER);

            cellStyle = new Style()
                    .setFont(normalFont)
                    .setFontSize(10)
                    .setPadding(5)
                    .setTextAlignment(TextAlignment.CENTER);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Style getCenteredTableStyle() {
        return new Style()
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMarginTop(10)
                .setMarginBottom(10);
    }

    public static PdfFont getTitleFont() { return titleFont; }
    public static PdfFont getNormalFont() { return normalFont; }
    public static Style getHeaderStyle() { return headerStyle; }
    public static Style getCellStyle() { return cellStyle; }
}