package com.zt.utils;

import com.alibaba.fastjson.JSONArray;
import com.csvreader.CsvReader;

import java.io.InputStream;
import java.nio.charset.Charset;

public class Utils {

    public static JSONArray readCsv(InputStream fi, String encode) throws Exception {
        JSONArray result = new JSONArray();
        CsvReader cr = new CsvReader(fi, Charset.forName(encode));
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
        fi.close();

        return result;
    }
}
