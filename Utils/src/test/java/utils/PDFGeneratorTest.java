package utils;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFGeneratorTest {

    @Test
    void testCreateCellWithValidContent() {
        // Test with normal string content
        String testContent = "Test Cell Content";
        Cell cell = PDFGenerator.createCell(testContent);

        assertNotNull(cell, "Cell should not be null");
        assertEquals(1, cell.getChildren().size(), "Cell should contain one child element");

        // Verify the content is properly wrapped in a Paragraph
        Object child = cell.getChildren().get(0);
        assertTrue(child instanceof Paragraph, "Child should be a Paragraph");
        Paragraph paragraph = (Paragraph) child;
        assertEquals(testContent, paragraph, "Content should match input");
    }

    @Test
    void testCreateCellWithEmptyString() {
        // Test with empty string
        Cell cell = PDFGenerator.createCell("");

        assertNotNull(cell, "Cell should not be null even with empty content");
        assertEquals(1, cell.getChildren().size(), "Cell should contain one child element");

        Paragraph paragraph = (Paragraph) cell.getChildren().get(0);
        assertEquals("", paragraph, "Content should be empty string");
    }

    @Test
    void testCreateCellWithNullContent() {
        // Test with null content
        Cell cell = PDFGenerator.createCell(null);

        assertNotNull(cell, "Cell should not be null even with null content");
        assertEquals(1, cell.getChildren().size(), "Cell should contain one child element");

        Paragraph paragraph = (Paragraph) cell.getChildren().get(0);
        assertEquals("", paragraph, "Content should be empty string for null input");
    }

    @Test
    void testCreateCellWithSpecialCharacters() {
        // Test with special characters
        String testContent = "Content with spéciäl ch@racters! 123";
        Cell cell = PDFGenerator.createCell(testContent);

        Paragraph paragraph = (Paragraph) cell.getChildren().get(0);
        assertEquals(testContent, paragraph, "Should handle special characters");
    }

    @Test
    void testCreateCellReturnsNewInstance() {
        // Verify each call returns a new instance
        Cell cell1 = PDFGenerator.createCell("Test 1");
        Cell cell2 = PDFGenerator.createCell("Test 2");

        assertNotSame(cell1, cell2, "Each call should return a new Cell instance");
        assertNotEquals(
                ((Paragraph)cell1.getChildren().get(0)),
                ((Paragraph)cell2.getChildren().get(0)),
                "Cells should have different content"
        );
    }

    @Test
    void testCellDefaultProperties() {
        Cell cell = PDFGenerator.createCell("Test");

        // Verify default cell properties
        assertFalse(cell.hasProperty(com.itextpdf.layout.properties.Property.BORDER), "Cell should not have border by default");
        assertEquals(0, cell.getPaddingLeft());
        assertEquals(0, cell.getPaddingRight());
        assertNull(cell);
    }
}