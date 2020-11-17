package com.berg.file;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 导出Excel公共方法（是SXSSF导出方式）注意：只能写导出数据不能写导入数据
 * 注意：.xls格式最大导出行数65536，最大列数256列，.xlsx格式最大导出行数1048576，最大列数16348
 *
 */
@Slf4j
public class SXSSFExcelUtil {

    private SXSSFWorkbook workbook;
    private Sheet sheet;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String path;

    public SXSSFExcelUtil(){
        this.workbook = new SXSSFWorkbook();
        this.sheet = this.workbook.createSheet("sheet");
    }

    public SXSSFExcelUtil(String path, SXSSFWorkbook workbook, SXSSFSheet sheet){
        this.workbook = workbook;
        this.sheet = sheet;
        this.path = path;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    /**
     * dataList:数据（暂时不支持分页插入数据）
     * headers：标题
     * isStyle：是否使用标题风格设置
     */
    public void exportExcel(List<Object[]> dataList, String[] headers, boolean isStyle) {

        CellStyle styleForHeader = this.setStyleForHeader();
        CellStyle styleForBody = this.setStyleForBody();
        Row row = null;

        // 产生表格标题行
        row = sheet.createRow(0);
        row.setHeight((short) (25 * 35)); //设置高度
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            if(isStyle) {
                cell.setCellStyle(styleForHeader);
            }
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //将查询出的数据设置到sheet对应的单元格中
        for(int i=0;i<dataList.size();i++){

            Object[] obj = dataList.get(i);//遍历每个对象
            row = sheet.createRow(i+1);//创建所需的行数

            row.setHeight((short) (25 * 20)); //设置高度

            for(int j=0; j<obj.length; j++){
                Cell cell = null;   //设置单元格的数据类型
                cell = row.createCell(j, Cell.CELL_TYPE_STRING);
                if(!"".equals(obj[j]) && obj[j] != null){
                    cell.setCellValue(obj[j].toString());                        //设置单元格的值
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(styleForBody);                                    //设置单元格样式
            }
        }
    }

    /**
     * 导出
     * @param dataList
     */
    public void exportExcel(List<List<ExportVo>> dataList) {
        Row row = null;
        //将查询出的数据设置到sheet对应的单元格中
        for(int i=0;i<dataList.size();i++){
            List<ExportVo> obj = dataList.get(i);//遍历每个对象
            row = sheet.createRow(i+1);//创建所需的行数
            row.setHeight((short) (25 * 20)); //设置高度
            for(int j=0;j<obj.size();j++) {
                Cell cell = null;   //设置单元格的数据类型
                cell = row.createCell(j, SXSSFCell.CELL_TYPE_STRING);
                if(!"".equals(obj.get(j).data) && obj.get(j).data != null){
                    cell.setCellValue(obj.get(j).data.toString());//设置单元格的值
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(obj.get(j).style);
            }
        }
    }

    @Data
    public class ExportVo {

        public Object data;

        public CellStyle style;

        public ExportVo(Object data,CellStyle style){
            this.data = data;
            this.style = style;
        }
    }

    /**
     * 头部风格设计
     * @return
     */
    private CellStyle setStyleForHeader() {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //边框
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        //对齐
        style.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        style.setFont(font);

        return style;
    }

    /**
     * 文件内容风格设计
     * @return
     */
    private CellStyle setStyleForBody() {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);

        return style;
    }

    /**
     * 内容转换
     * @param fieldName
     * @param textValue
     * @return
     */
    private String convertData(String fieldName, String textValue) {
        if (textValue != null && !"".equals(textValue.trim())) {
            if ("sex".equalsIgnoreCase(fieldName)) {
                if ("0".equals(textValue)) {
                    textValue = "女";
                } else if ("1".equals(textValue)) {
                    textValue = "男";
                }
            }
            if ("state".equalsIgnoreCase(fieldName)) {
                if ("N".equals(textValue)) {
                    textValue = "未中奖";
                } else if ("Y".equals(textValue)) {
                    textValue = "中奖";
                }
            }
        } else {
            return "";
        }
        return textValue;
    }

    public void writeToClient(String fileName, HttpServletResponse response) {
        response.setContentType("application/binary;charset=ISO8859-1");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToLocal(HttpServletResponse response) {
        FileOutputStream out = null;
        try {
            String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xlsx";
            String headStr = "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8");
            out = new FileOutputStream(path+"/"+fileName);
            workbook.write(out);

            FileInputStream fis = new FileInputStream(path+"/"+fileName);
            byte[] buffer = IOUtils.toByteArray(fis);
            response.reset();   //清空缓存
            response.addHeader("Content-Disposition", headStr);
            response.addHeader("Content-Length", "" + buffer.length);
            response.setContentType("application/octet-stream;");
            response.setHeader("connection", "close");
            response.getOutputStream().write(buffer);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            out.close();
            fis.close();
            //删除本地文件
            File file=new File(path+"/"+fileName);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] writeToLocal(){
        byte[] buffer=null;
        FileOutputStream out = null;
        try {
            String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xlsx";
            String headStr = "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8");
            out = new FileOutputStream(path+"/"+fileName);
            workbook.write(out);

            FileInputStream fis = new FileInputStream(path+"/"+fileName);
            buffer = IOUtils.toByteArray(fis);
            out.close();
            fis.close();
            //删除本地文件
            File file=new File(path+"/"+fileName);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }
}
