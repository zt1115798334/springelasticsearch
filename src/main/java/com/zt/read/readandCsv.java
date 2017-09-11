package com.zt.read;

import com.alibaba.fastjson.JSONArray;
import com.zt.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class readandCsv {
    public static void main(String[] args) {
        try {
            File f = new File("E://red.csv");
            InputStream fi = new FileInputStream(f);

            JSONArray jsonArray = Utils.readCsv(fi, "GB18030");
            System.out.println("jsonArray = " + jsonArray);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
