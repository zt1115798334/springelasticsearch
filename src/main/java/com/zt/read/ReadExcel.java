package com.zt.read;

import com.alibaba.fastjson.JSONArray;
import com.zt.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ReadExcel {
    public static void main(String[] args) {
        try {
            File file = new File("E://template (1).xls");
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println("suffix = " + suffix);
            InputStream fi = new FileInputStream(file);
            JSONArray jsonArray = Utils.readExcel(fi, suffix);
            System.out.println("jsonArray = " + jsonArray);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
