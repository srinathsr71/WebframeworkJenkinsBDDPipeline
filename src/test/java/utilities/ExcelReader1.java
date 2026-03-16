package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader1 {

    // Generic method to read Excel data based on column names
    public static List<Map<String, String>> readExcelData(String filePath) throws IOException {
        List<Map<String, String>> excelData = new ArrayList<>();

        // Open the Excel file
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));

        // Create Workbook instance
        Workbook workbook = null;
        if (filePath.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(fileInputStream); // For .xlsx format
        } else if (filePath.endsWith(".xls")) {
            workbook = new HSSFWorkbook(fileInputStream); // For .xls format
        }

        // Access the first sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get the header row (first row) to map column names
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> columnMap = new HashMap<>();

        // Iterate over the header row to map column names to their indices
        for (int columnIndex = 0; columnIndex < headerRow.getPhysicalNumberOfCells(); columnIndex++) {
            Cell headerCell = headerRow.getCell(columnIndex);
            if (headerCell != null) {
                columnMap.put(headerCell.getStringCellValue(), columnIndex);
            }
        }

        // Iterate over the rows and extract data based on column names
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            Map<String, String> rowData = new HashMap<>();

            // For each column, get the value using the column name
            for (String columnName : columnMap.keySet()) {
                int columnIndex = columnMap.get(columnName);
                Cell cell = row.getCell(columnIndex);
                String cellValue = getCellValue(cell);
                rowData.put(columnName, cellValue); // Map column name to cell value
            }

            excelData.add(rowData);
        }

        // Close the workbook and file input stream
        workbook.close();
        fileInputStream.close();

        return excelData;
    }

    // Method to get the value of a cell as a string
    private static String getCellValue(Cell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    value = cell.getCellFormula();
                    break;
                default:
                    value = "";
            }
        }
        return value;
    }

    public List<String> getcoulmndata(String filepath, String columnname) {
        List<String> columndata = new ArrayList<>();
        try {
            List<Map<String, String>> data = readExcelData(filepath);
            for (Map<String, String> row : data) {
                String username = row.get(columnname); // Retrieve based on column name
                // String password = row.get("Password");
                System.out.println("Code is : " + username);
                columndata.add(username);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return columndata;
    }
    // Test the ExcelReader
//        public static void main(String[] args) {
//            new ExcelReader1().getcoulmndata("C:\\Users\\srinath\\Desktop\\DemoExcelReader.xlsx","Code");
//    }
}
