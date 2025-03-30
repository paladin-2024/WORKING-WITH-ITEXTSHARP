package utils;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.text.Paragraph;

public class PDFGenerator {
    public static Cell createCell(String content) {
        return new Cell().add((IBlockElement) new Paragraph(content));
    }
}