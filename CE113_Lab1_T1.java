
import java.io.*;
import java.util.*;
//org.apache.poi.xssf to work with XSLX files
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//org.apache.poi.xssf to work with XSL files
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

//org.apache.poi.ss to work with MS files its elements
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;

class XlsToCsv {
    static void xls(File ip, File op, Boolean flag) {
        StringBuffer data = new StringBuffer();
        try {
            FileOutputStream fos = new FileOutputStream(op);
            //
            InputStream ips = new FileInputStream(ip);
            Workbook wbook;
            Sheet sheet;
            if (flag) {
                wbook = new XSSFWorkbook(ips);
                sheet = (XSSFSheet) wbook.getSheetAt(0);
            } else {
                wbook = new HSSFWorkbook(ips);
                // loading sheet no 0 from all sheets in xsl file
                sheet = (HSSFSheet) wbook.getSheetAt(0);
            }

            //

            // org.apache.poi.ss.usermodel.Cell cell;
            // org.apache.poi.ss.usermodel.Row row;
            Cell cell;
            Row row;

            // Row iterator
            Iterator<Row> rItr = sheet.iterator();
            while (rItr.hasNext()) {
                row = rItr.next();
                // Cell Iterator
                Iterator<Cell> cItr = row.cellIterator();

                while (cItr.hasNext()) {
                    cell = cItr.next();
                    // Checking Cell type
                    CellType type = cell.getCellType();
                    if (type == CellType.STRING || type == CellType.BLANK) {
                        data.append(cell.getStringCellValue() + ',');
                    } else if (type == CellType.BOOLEAN) {
                        data.append(cell.getBooleanCellValue());
                        data.append(',');
                    } else if (type == CellType.NUMERIC) {
                        data.append(cell.getNumericCellValue() + ',');
                    } else if (type == CellType.ERROR) {
                        data.append(cell.getErrorCellValue() + ',');
                    } else {
                        data.append(cell.toString() + ',');
                    }
                    // System.out.println(cell.getCellTypeEnum());
                }
                data.append("\n");
            }
            fos.write(data.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select Excel FIle TYpe\n0). XSL\n1).XSLX\n");
        int f = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Source File name without extension:\t");
        String ipath = sc.nextLine();
        File ip;
        if (f > 0) {
            ip = new File(ipath + ".xlsx");
        } else {
            ip = new File(ipath + ".xls");
        }
        System.out.println("Enter Output File name without extension:\t");
        String opath = sc.nextLine();
        File op = new File(opath + ".csv");
        sc.close();
        xls(ip, op, f > 0);
    }
}