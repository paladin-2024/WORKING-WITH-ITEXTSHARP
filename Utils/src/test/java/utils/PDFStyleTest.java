package utils;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Style;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFStyleTest {

    @BeforeAll
    static void setup() {
        // Ensure static initialization is complete
        assertNotNull(PDFStyle.getTitleFont());
    }

    @Test
    void testTitleFontInitialization() {
        PdfFont font = PDFStyle.getTitleFont();
        assertNotNull(font, "Title font should not be null");
        assertTrue(font.getFontProgram().getFontNames().getFontName().contains("Helvetica-Bold"));
    }

    @Test
    void testNormalFontInitialization() {
        PdfFont font = PDFStyle.getNormalFont();
        assertNotNull(font, "Normal font should not be null");
        assertTrue(font.getFontProgram().getFontNames().getFontName().contains("Helvetica"));
    }

    @Test
    void testStyleConsistency() {
        // Verify same instance is returned for singleton-like styles
        assertSame(PDFStyle.getTitleFont(), PDFStyle.getTitleFont());
        assertSame(PDFStyle.getNormalFont(), PDFStyle.getNormalFont());
        assertSame(PDFStyle.getHeaderStyle(), PDFStyle.getHeaderStyle());
        assertSame(PDFStyle.getCellStyle(), PDFStyle.getCellStyle());
    }

    @Test
    void testNewTableStyleInstance() {
        // Verify getCenteredTableStyle returns new instance each time
        Style style1 = PDFStyle.getCenteredTableStyle();
        Style style2 = PDFStyle.getCenteredTableStyle();
        assertNotSame(style1, style2, "Should return new instance each time");
    }
}