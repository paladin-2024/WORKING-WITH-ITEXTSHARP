package utils;

import static org.junit.jupiter.api.Assertions.*;

class PDFStyleTest {
    // Test the PDFStyle class
    @org.junit.jupiter.api.Test
    void testPDFStyle() {
        PDFStyle pdfStyle = new PDFStyle();
        pdfStyle.setFontSize(12);
        pdfStyle.setFontName("Arial");
        pdfStyle.setFontColor("Black");
        pdfStyle.setBackgroundColor("White");

        // Check if the font size is set correctly
        assertEquals(12, pdfStyle.getFontSize());

        // Check if the font name is set correctly
        assertEquals("Arial", pdfStyle.getFontName());

        // Check if the font color is set correctly
        assertEquals("Black", pdfStyle.getFontColor());

        // Check if the background color is set correctly
        assertEquals("White", pdfStyle.getBackgroundColor());
    }
}