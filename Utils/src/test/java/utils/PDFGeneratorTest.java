package utils;

import static org.junit.jupiter.api.Assertions.*;

class PDFGeneratorTest {
    // Test the PDFGenerator class
    @org.junit.jupiter.api.Test
    void testPDFGenerator() {
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.setTitle("Test Title");
        pdfGenerator.setAuthor("Test Author");
        pdfGenerator.setSubject("Test Subject");
        pdfGenerator.setKeywords("Test Keywords");

        // Check if the title is set correctly
        assertEquals("Test Title", pdfGenerator.getTitle());

        // Check if the author is set correctly
        assertEquals("Test Author", pdfGenerator.getAuthor());

        // Check if the subject is set correctly
        assertEquals("Test Subject", pdfGenerator.getSubject());

        // Check if the keywords are set correctly
        assertEquals("Test Keywords", pdfGenerator.getKeywords());
    }
}