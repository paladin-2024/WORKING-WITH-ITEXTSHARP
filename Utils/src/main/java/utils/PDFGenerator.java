package utils;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.text.Paragraph;

public class PDFGenerator {
    public static Cell createCell(String content) {
        Cell cell = new Cell().add((IBlockElement) new Paragraph(content));
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER); // Example alignment fix
        return cell;
    }
}
