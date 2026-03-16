package utilities;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public static List<List<String>> getRowData(String sheetName) {
        List<List<String>> rowData = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")
                    + "/src/test/resources/ExcelFile/order_enquiry_upload_template.xlsx"));
            Workbook workbook = WorkbookFactory.create(fis);

            int sheetCount = workbook.getNumberOfSheets();
            Sheet sheet = workbook.getSheet(sheetName);
            // Iterate through each sheet

            // Iterate through each row
            for (Row row : sheet) {
                List<String> cellData = new ArrayList<>();
                if (row.getRowNum() != 0) {
                    // Iterate through each cell
                    for (Cell cell : row) {
                        String cellValue = "";
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        cellValue = cell.getDateCellValue().toString();
                                    } else {
                                        cellValue = Double.toString(cell.getNumericCellValue());
                                    }
                                    break;
                                case BOOLEAN:
                                    cellValue = Boolean.toString(cell.getBooleanCellValue());
                                    break;
                                case FORMULA:
                                    cellValue = cell.getCellFormula();
                                    break;
                                default:
                                    cellValue = "";
                            }
                        }
                        cellData.add(cellValue);
                    }
                    rowData.add(cellData);
                }
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowData;
    }
}
