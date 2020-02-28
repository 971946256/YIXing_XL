package com.otitan.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.otitan.model.AddressModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Utli {

    /**
     * 从assets目录下拷贝文件
     *
     * @param context            上下文
     * @param assetsFilePath     文件的路径名如：SBClock/0001cuteowl/cuteowl_dot.png
     * @param targetFileFullPath 目标文件路径如：/sdcard/SBClock/0001cuteowl/cuteowl_dot.png
     */
    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        File targetFile = new File(targetFileFullPath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        Log.d("Tag", "copyFileFromAssets ");
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = context.getAssets().open(assetsFilePath);
            copyFile(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            Log.d("Tag", "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void copyFile(InputStream in, String targetPath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(targetPath));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = in.read(buffer)) != -1) {// 循环从输入流读取
                // buffer字节
                fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
            }
            fos.flush();// 刷新缓冲区
            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }


    public static void writeExcel(File file, ArrayList<AddressModel> args) throws BiffException, IOException, RowsExceededException, WriteException {
        Workbook book = Workbook.getWorkbook(file);
        Sheet sheet = book.getSheet(0);
        //获取行
        int length = sheet.getRows();
        WritableWorkbook workbook = Workbook.createWorkbook(file, book);
        WritableSheet writableSheet = workbook.getSheet(0);
        // 从最后一行开始加
        for (int i = 0; i < args.size(); i++) {
            //姓名
            Label label1 = new Label(0, i + length, args.get(i).getName());
            //电话
            Label label2 = new Label(1, i + length, args.get(i).getPhone());
            String[] addressArray = args.get(i).getAddress().split(" ");
            //省
            Label label3 = new Label(3, i + length, addressArray[0]);
            //市
            Label label4 = new Label(4, i + length, addressArray[1]);
            //区县
            Label label5 = new Label(5, i + length, addressArray[2]);
            //具体地址
            Label label6 = new Label(6, i + length, addressArray[3]);
            //备注
            Label label7 = new Label(8, i + length, args.get(i).getRemarks());
            writableSheet.addCell(label1);
            writableSheet.addCell(label2);
            writableSheet.addCell(label3);
            writableSheet.addCell(label4);
            writableSheet.addCell(label5);
            writableSheet.addCell(label6);
            writableSheet.addCell(label7);
        }
        workbook.write();
        workbook.close();
    }

    public static void writeExcelSimple(File file, ArrayList<AddressModel> args) {
        Workbook book = null;
        try {
            book = Workbook.getWorkbook(file);
            Sheet sheet = book.getSheet(0);
            //获取行
            int length = sheet.getRows();
            WritableWorkbook workbook = Workbook.createWorkbook(file, book);
            WritableSheet writableSheet = workbook.getSheet(0);
            // 从最后一行开始加
            for (int i = 0; i < args.size(); i++) {
                //信息
                Label label1 = new Label(0, i + length, args.get(i).getName() + " " + args.get(i).getPhone() + " " + args.get(i).getAddress());
                //备注
                Label label2 = new Label(1, i + length, args.get(i).getRemarks());
                writableSheet.addCell(label1);
                writableSheet.addCell(label2);
            }
            workbook.write();
            workbook.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }


    }

    public static ArrayList<String> readCSV(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String path = Environment.getExternalStorageDirectory() + "/ABC/" + fileName;
            File csv = new File(path);
            FileInputStream stream = new FileInputStream(csv);
            Scanner scanner = new Scanner(stream, "UTF-8");
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                list.add(str);
                Log.e("tag", str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
