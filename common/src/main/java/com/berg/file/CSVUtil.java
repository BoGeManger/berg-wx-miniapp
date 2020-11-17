package com.berg.file;

import com.berg.constant.AppConstants;
import com.berg.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class CSVUtil {
    
    //行尾分隔符定义
    final static String NEW_LINE_SEPARATOR = "\n";

    /**
     * 创建CSV文件
     * @param head
     * @param values
     * @return
     * @throws IOException
     */
    public static byte[] makeTempCSV(String[] head, List<Object[]> values) throws IOException {
        // 创建文件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String  path = sdf.format(new Date()) + ".csv";
        File file = new File(path);
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        osw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
        BufferedWriter bufferedWriter =
                new BufferedWriter(osw);
        CSVPrinter printer = new CSVPrinter(bufferedWriter, formator);
        // 写入表头
        printer.printRecord(head);
        // 写入内容
        for (Object[] value : values) {
            printer.printRecord(value);
        }
        printer.close();
        bufferedWriter.close();
        FileInputStream fis = new FileInputStream(file.getPath());
        byte[] buffer = IOUtils.toByteArray(fis);
        fis.close();
        file.delete();
        return buffer;
    }

    /**
     * 读取CSV文件的内容（不含表头）
     * @param bytes
     * @param colNum
     * @return
     */
    public static List<List<Object>> readCSV(byte[] bytes, int colNum) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        File f = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Path path =  Paths.get( sdf.format(new Date())+".csv");
            Files.write(path, bytes);
            f = new File(path.toString());
            fileInputStream = new FileInputStream(f);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);
            // 表内容集合，外层List为行的集合，内层List为字段集合
            List<List<Object>> values = new ArrayList<>();
            int rowIndex = 0;
            for (CSVRecord record : parser.getRecords()) {
                // 跳过表头
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                //每行的内容
                List<Object> value = new ArrayList<>(colNum + 1);
                for (int i = 0; i < colNum; i++) {
                    value.add(record.get(i));
                }
                values.add(value);
                rowIndex++;
            }
            return values;
        } catch (IOException e) {
            log.error("解析CSV内容失败" + e.getMessage(), e);
        }finally {
            //关闭流
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            f.delete();
        }
        return null;
    }

    /**
     * 读取CSV文件的内容（不含表头）
     * @param bytes
     * @return
     */
    public static List<Object> readOneCSV(byte[] bytes) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        File f = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Path path =  Paths.get( sdf.format(new Date())+".csv");
            Files.write(path, bytes);
            f = new File(path.toString());
            fileInputStream = new FileInputStream(f);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withQuote(null);
            CSVParser parser = new CSVParser(bufferedReader, csvFileFormat);
            // 表内容集合，外层List为行的集合，内层List为字段集合
            List<Object> values = new ArrayList<>();
            int rowIndex = 0;
            for (CSVRecord record : parser.getRecords()) {
                // 跳过表头
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                values.add(record.get(0));
                rowIndex++;
            }
            return values;
        } catch (IOException e) {
            log.error("解析CSV内容失败" + e.getMessage(), e);
        }finally {
            //关闭流
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            f.delete();
        }
        return null;
    }
}
