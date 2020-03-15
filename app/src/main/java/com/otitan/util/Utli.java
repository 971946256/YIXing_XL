package com.otitan.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.otitan.model.AddressModel;
import com.otitan.model.CustomerInfo;

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

import jxl.Cell;
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
        return new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
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


    /**
     * 写弈星客户信息表
     *
     * @param file       目标文件
     * @param customInfo 用户信息
     * @throws BiffException
     * @throws IOException
     * @throws RowsExceededException
     * @throws WriteException
     */
    public static void writeExcelYX(File file, CustomerInfo customInfo) throws BiffException, IOException, RowsExceededException, WriteException {
        Workbook book = Workbook.getWorkbook(file);
        Sheet sheet = book.getSheet(0);
        //获取行
        int length = sheet.getRows();
        WritableWorkbook workbook = Workbook.createWorkbook(file, book);
        WritableSheet writableSheet = workbook.getSheet(0);
        //添加时间
        Label label0 = new Label(0, length, FormatUtil.dateFormat());
        //姓名
        Label label1 = new Label(1, length, customInfo.getName());
        //电话
        Label label2 = new Label(2, length, customInfo.getPhone());
        //地址
        Label label3 = new Label(3, length, customInfo.getAddress());
        //面积
        Label label4 = new Label(4, length, customInfo.getArea());
        //预计花粉总使用量 g
        Label label5 = new Label(5, length, customInfo.getUsage());
        //基地产量 亩/斤
        Label label6 = new Label(6, length, customInfo.getYields());
        //19年果价
        Label label7 = new Label(7, length, customInfo.getPrice19());
        //预期果价
        Label label8 = new Label(8, length, customInfo.getPrice());
        //19年是否收到尾款 true是 false否
        Label label9 = new Label(9, length, customInfo.getTailMoney() ? "是" : "否");
        //备注
        Label label10 = new Label(10, length, customInfo.getRemark());
        //操作人
//        Label label11 = new Label(10, length, customInfo.getRemark());

        writableSheet.addCell(label0);
        writableSheet.addCell(label1);
        writableSheet.addCell(label2);
        writableSheet.addCell(label3);
        writableSheet.addCell(label4);
        writableSheet.addCell(label5);
        writableSheet.addCell(label6);
        writableSheet.addCell(label7);
        writableSheet.addCell(label8);
        writableSheet.addCell(label9);
        writableSheet.addCell(label10);
//        writableSheet.addCell(label11);
        workbook.write();
        workbook.close();
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

    public static ArrayList<CustomerInfo> readCustomerInfo(File file) {
        Workbook book;
        // 输入流
        InputStream is = null;
        ArrayList<CustomerInfo> list = new ArrayList<>();
        try {
            // 加载Excel文件
            is = new FileInputStream(file);
            book = Workbook.getWorkbook(file);
            Sheet sheet = book.getSheet(0);
            Cell cell = null;// 单个单元格
            //行遍历
            for (int j = 1; j < sheet.getRows(); j++) {
                CustomerInfo info = new CustomerInfo();
                //列遍历
                info.setTime(sheet.getCell(0, j).getContents());
                info.setName(sheet.getCell(1, j).getContents());
                info.setPhone(sheet.getCell(2, j).getContents());
                info.setAddress(sheet.getCell(3, j).getContents());
                info.setArea(sheet.getCell(4, j).getContents());
                info.setUsage(sheet.getCell(5, j).getContents());
                info.setYields(sheet.getCell(6, j).getContents());
                info.setPrice19(sheet.getCell(7, j).getContents());
                info.setPrice(sheet.getCell(8, j).getContents());
                info.setTailMoney(sheet.getCell(9, j).getContents().equals("是"));
                info.setRemark(sheet.getCell(10, j).getContents());
                list.add(info);
            }

        } catch (IOException e) {
            Log.e("tag", e.getMessage());
            e.printStackTrace();
        } catch (BiffException e) {
            Log.e("tag", e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
