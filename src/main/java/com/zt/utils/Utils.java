package com.zt.utils;

import com.alibaba.fastjson.JSONArray;
import com.csvreader.CsvReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Utils {

    public static JSONArray readCsv(InputStream is, String encode) throws Exception {
        JSONArray result = new JSONArray();
        CsvReader cr = new CsvReader(is, Charset.forName(encode));
        while (cr.readRecord()) {
            String[] rs = cr.getValues();
            JSONArray ja = new JSONArray();
            for (String s : rs) {
                ja.add(s);
                System.out.print("[" + s + "]");
            }
            result.add(ja);
            System.out.print("\n");
        }
        cr.close();
        is.close();

        return result;
    }

    public static JSONArray readExcel(InputStream is, String fileType) throws IOException {
        JSONArray result = new JSONArray();
        Workbook wb = null;

        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(is);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(is);
        }
        int sheetSize = wb.getNumberOfSheets();
        for (int i = 0; i < sheetSize; i++) {//遍历sheet页
            Sheet sheet = wb.getSheetAt(i);
            int rowSize = sheet.getLastRowNum() + 1;
            JSONArray rowJA = new JSONArray();
            for (int j = 0; j < rowSize; j++) {//遍历行
                Row row = sheet.getRow(j);
                if (row == null) {//略过空行
                    continue;
                }
                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                JSONArray cellJA = new JSONArray();
                for (int k = 0; k < cellSize; k++) {
                    Cell cell = row.getCell(k);
                    String value;
                    if (cell != null) {
                        value = cell.toString();
                        cellJA.add(value);
                    }
                }
                rowJA.add(cellJA);
            }
            result.add(rowJA);
        }
        return result;
    }
}
