package AmazonDataScrapping;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

private Workbook workbook;
    private Sheet sheet;
    private int rowCount = 0;
 
    public ExcelWriter(String filePath, String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
    }
 
    public void writeRow(String[] data) {
        Row row = sheet.createRow(rowCount++);
        for (int i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data[i]);
        }
    }
    public void setColumnWidth(int columnIndex, int width) {
        sheet.setColumnWidth(columnIndex, width);
    }
    public void setCellColor(int rowIndex, int columnIndex, short colorIndex) {
        Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(colorIndex);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
    }
 
    public void save(String filePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
