package com.example.dfh.planethunder.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by dfh on 19-8-30.
 */

public class DimenTool {

    public static void gen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        File file = new File("./app/src/main/res/values/dimens.xml");
        BufferedReader reader = null;
        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        try {
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                            (tempString.substring(tempString.indexOf(">") + 1,
                                    tempString.indexOf("</dimen>") - 2));
                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。

                    sw320.append(start).append(num * 0.8).append(end).append("\r\n");
                    sw600.append(start).append(num * 1.5).append(end).append("\r\n");
                    sw720.append(start).append(num * 1.8).append(end).append("\r\n");

                } else {
                    sw320.append(tempString).append("");
                    sw600.append(tempString).append("");
                    sw720.append(tempString).append("");
                }
                line++;
            }
            reader.close();
            System.out.println("<!--  sw320 -->");
            System.out.println(sw320);

            System.out.println("<!--  sw600 -->");
            System.out.println(sw600);
            System.out.println("<!--  sw720 -->");
            System.out.println(sw720);

            String sw320file = "./app/src/main/res/values-sw320dp-land/dimens.xml";
            String sw600file = "./app/src/main/res/values-sw600dp-land/dimens.xml";
            String sw720file = "./app/src/main/res/values-sw720dp-land/dimens.xml";

            //将新的内容，写入到指定的文件中去
            writeFile(sw320file, sw320.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw720file, sw720.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    public static void main(String[] args) {
        gen();
    }
}

