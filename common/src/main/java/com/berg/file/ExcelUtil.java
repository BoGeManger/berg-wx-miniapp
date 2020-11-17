package com.berg.file;

import com.berg.constant.AppConstants;
import com.berg.utils.SpringUtil;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    /**
     * 标题样式
     */
    final static String STYLE_HEADER = "header";
    /**
     * 表头样式
     */
    final static String STYLE_TITLE = "title";
    /**
     * 数据样式
     */
    final static String STYLE_DATA = "data";

    /**
     * 存储样式
     */
    static final HashMap<String, CellStyle> cellStyleMap = new HashMap<>();

    /**
     * 读取excel文件里面的内容 支持日期，数字，字符，函数公式，布尔类型
     * @param file
     * @param rowCount
     * @param columnCount
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<ExcelSheetPO> readExcel(MultipartFile file, Integer rowCount, Integer columnCount)
            throws FileNotFoundException, IOException {
        // 根据后缀名称判断excel的版本
        Path path=null;
        InputStream is=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Workbook workbook = null;
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String name = fileName.substring(fileName.lastIndexOf("."));
            path = Paths.get(sdf.format(new Date()) + name);
            Files.write(path, bytes);
            File f = new File(path.toString());
            is = new FileInputStream(f);
            Workbook wb = null;
            if (file.getOriginalFilename().endsWith("xls")) {
                wb = new HSSFWorkbook(is); // 2003版本
            } else if (file.getOriginalFilename().endsWith("xlsx")) {
                wb = new XSSFWorkbook(is); // 2007版本
            } else {
                // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
                throw new IllegalArgumentException("excel后缀格式异常");
            }
            // 开始读取数据
            List<ExcelSheetPO> sheetPOs = new ArrayList<>();
            // 解析sheet
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                List<List<Object>> dataList = new ArrayList<>();
                ExcelSheetPO sheetPO = new ExcelSheetPO();
                sheetPO.setSheetName(sheet.getSheetName());
                sheetPO.setDataList(dataList);
                int readRowCount = 0;
                if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                    readRowCount = sheet.getPhysicalNumberOfRows();
                } else {
                    readRowCount = rowCount;
                }
                // 解析sheet 的行
                for (int j = 1; j < readRowCount; j++) {
                    Row row = sheet.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    if (row.getFirstCellNum() < 0) {
                        continue;
                    }
                    int readColumnCount = 0;
                    if (columnCount == null || columnCount > row.getLastCellNum()) {
                        readColumnCount = (int) row.getLastCellNum();
                    } else {
                        readColumnCount = columnCount;
                    }
                    List<Object> rowValue = new LinkedList<Object>();
                    // 解析sheet 的列
                    for (int k = 0; k < readColumnCount; k++) {
                        Cell cell = row.getCell(k);
                        rowValue.add(getCellValue(wb, cell));
                    }
                    dataList.add(rowValue);
                }
                sheetPOs.add(sheetPO);
            }
            return sheetPOs;
        }catch (Exception e){
            throw (e);
        }finally {
            IOUtils.closeQuietly(is);
            Files.deleteIfExists(path);
        }
    }

    public static List<ExcelSheetOnePO> readExcelOne(MultipartFile file, Integer rowCount)
            throws FileNotFoundException, IOException {

        // 根据后缀名称判断excel的版本
        Path path=null;
        InputStream is=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Workbook workbook = null;
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String name = fileName.substring(fileName.lastIndexOf("."));
            path = Paths.get(sdf.format(new Date()) + name);
            Files.write(path, bytes);
            File f = new File(path.toString());
            is = new FileInputStream(f);
            Workbook wb = null;
            if (file.getOriginalFilename().endsWith("xls")) {
                wb = new HSSFWorkbook(is); // 2003版本
            } else if (file.getOriginalFilename().endsWith("xlsx")) {
                wb = new XSSFWorkbook(is); // 2007版本
            } else {
                // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
                throw new IllegalArgumentException("excel后缀格式异常");
            }
            // 开始读取数据
            List<ExcelSheetOnePO> sheetPOs = new ArrayList<>();
            // 解析sheet
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                List<Object> dataList = new ArrayList<>();
                ExcelSheetOnePO sheetPO = new ExcelSheetOnePO();
                sheetPO.setSheetName(sheet.getSheetName());
                sheetPO.setDataList(dataList);
                int readRowCount = 0;
                if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                    readRowCount = sheet.getPhysicalNumberOfRows();
                } else {
                    readRowCount = rowCount;
                }
                // 解析sheet 的行
                for (int j = 1; j < readRowCount; j++) {
                    Row row = sheet.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    if (row.getFirstCellNum() < 0) {
                        continue;
                    }
                    Cell cell = row.getCell(0);
                    dataList.add(getCellValue(wb, cell));
                }
                sheetPOs.add(sheetPO);
            }
            return sheetPOs;
        }catch (Exception e){
            throw (e);
        }finally {
            IOUtils.closeQuietly(is);
            Files.deleteIfExists(path);
        }
    }


    public static List<ExcelSheetOnePO> readExcelOne(byte[] filebytes,String fileExtension, Integer rowCount)
            throws FileNotFoundException, IOException {

        // 根据后缀名称判断excel的版本
        Path path=null;
        InputStream is=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Workbook workbook = null;
            byte[] bytes = filebytes;
            String fileName =  sdf.format(new Date())+"."+fileExtension;
            String name = fileName.substring(fileName.lastIndexOf("."));
            //Path path = Paths.get("src/main/resources/templates/" + sdf.format(new Date()) + name);
            path = Paths.get(sdf.format(new Date()) + name);
            Files.write(path, bytes);
            File f = new File(path.toString());
            is = new FileInputStream(f);
            Workbook wb = null;
            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook(is); // 2003版本
            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook(is); // 2007版本
            } else {
                // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
                throw new IllegalArgumentException("excel后缀格式异常");
            }
            // 开始读取数据
            List<ExcelSheetOnePO> sheetPOs = new ArrayList<>();
            // 解析sheet
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                List<Object> dataList = new ArrayList<>();
                ExcelSheetOnePO sheetPO = new ExcelSheetOnePO();
                sheetPO.setSheetName(sheet.getSheetName());
                sheetPO.setDataList(dataList);
                int readRowCount = 0;
                if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                    readRowCount = sheet.getPhysicalNumberOfRows();
                } else {
                    readRowCount = rowCount;
                }
                // 解析sheet 的行
                for (int j = 1; j < readRowCount; j++) {
                    Row row = sheet.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    if (row.getFirstCellNum() < 0) {
                        continue;
                    }
                    Cell cell = row.getCell(0);
                    dataList.add(getCellValue(wb, cell));
                }
                sheetPOs.add(sheetPO);
            }
            return sheetPOs;
        }catch (Exception e){
            throw (e);
        }finally {
            IOUtils.closeQuietly(is);
            Files.deleteIfExists(path);
        }
    }

    static Object getCellValue(Workbook wb, Cell cell) {
        Object columnValue = null;
        if (cell != null) {
            DecimalFormat df = new DecimalFormat("0");// 格式化 number
            // String
            // 字符
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
            DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    columnValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = df.format(cell.getNumericCellValue());
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = nf.format(cell.getNumericCellValue());
                    } else {
                        columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    columnValue = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    columnValue = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    columnValue = cellValue.getNumberValue();
                    break;
                default:
                    columnValue = cell.toString();
            }
        }
        return columnValue;
    }

    /**
     * 把excel表格写入输出流中，输出流会被关闭
     *
     * @param version
     * @param excelSheets
     * @param outStream
     * @param closeStream 是否关闭输出流
     * @throws IOException
     */
    public static void createWorkbookAtOutStream(ExcelVersion version, List<ExcelSheetPO> excelSheets,
                                                 OutputStream outStream, boolean closeStream) throws IOException {
        if (CollectionUtils.isNotEmpty(excelSheets)) {
            Workbook wb = createWorkBook(version, excelSheets);
            wb.write(outStream);
            if (closeStream) {
                outStream.close();
            }
        }
    }

    static Workbook createWorkBook(ExcelVersion version, List<ExcelSheetPO> excelSheets) {
        Workbook wb = createWorkbook(version);
        for (int i = 0; i < excelSheets.size(); i++) {
            ExcelSheetPO excelSheetPO = excelSheets.get(i);
            if (excelSheetPO.getSheetName() == null) {
                excelSheetPO.setSheetName("sheet" + i);
            }
            // 过滤特殊字符
            Sheet tempSheet = wb.createSheet(WorkbookUtil.createSafeSheetName(excelSheetPO.getSheetName()));
            buildSheetData(wb, tempSheet, excelSheetPO, version);
        }
        return wb;
    }

    static void buildSheetData(Workbook wb, Sheet sheet, ExcelSheetPO excelSheetPO, ExcelVersion version) {
        sheet.setDefaultRowHeight((short) 400);
        sheet.setDefaultColumnWidth((short) 10);
        createTitle(sheet, excelSheetPO, wb, version);
        createHeader(sheet, excelSheetPO, wb, version);
        createBody(sheet, excelSheetPO, wb, version);
    }

    static void createBody(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        List<List<Object>> dataList = excelSheetPO.getDataList();
        for (int i = 0; i < dataList.size() && i < version.getMaxRow(); i++) {
            List<Object> values = dataList.get(i);
            Row row = sheet.createRow(2 + i);
            for (int j = 0; j < values.size() && j < version.getMaxColumn(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(getStyle(STYLE_DATA, wb));
                cell.setCellValue(values.get(j).toString());
            }
        }

    }

    static void createHeader(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        String[] headers = excelSheetPO.getHeaders();
        Row row = sheet.createRow(1);
        for (int i = 0; i < headers.length && i < version.getMaxColumn(); i++) {
            Cell cellHeader = row.createCell(i);
            cellHeader.setCellStyle(getStyle(STYLE_HEADER, wb));
            cellHeader.setCellValue(headers[i]);
        }

    }

    static void createTitle(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
        Row titleRow = sheet.createRow(0);
        Cell titleCel = titleRow.createCell(0);
        titleCel.setCellValue(excelSheetPO.getTitle());
        titleCel.setCellStyle(getStyle(STYLE_TITLE, wb));
        // 限制最大列数
        int column = excelSheetPO.getDataList().size() > version.getMaxColumn() ? version.getMaxColumn()
                : excelSheetPO.getDataList().size();
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column - 1));
    }

    static CellStyle getStyle(String type, Workbook wb) {

        if (cellStyleMap.containsKey(type)) {
            return cellStyleMap.get(type);
        }
        // 生成一个样式
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setWrapText(true);

        if (STYLE_HEADER == type) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 16);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        } else if (STYLE_TITLE == type) {
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 18);
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        } else if (STYLE_DATA == type) {
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
        }
        cellStyleMap.put(type, style);
        return style;
    }

    static Workbook createWorkbook(ExcelVersion version) {
        switch (version) {
            case V2003:
                return new HSSFWorkbook();
            case V2007:
                return new XSSFWorkbook();
        }
        return null;
    }

    @Data
    public static class ExcelSheetOnePO{
        /**
         * sheet的名称
         */
        String sheetName;


        /**
         * 表格标题
         */
        String title;

        /**
         * 头部标题集合
         */
        String[] headers;

        /**
         * 数据集合(第一列)
         */
        List<Object> dataList;
    }

    @Data
    public static class ExcelSheetPO {

        /**
         * sheet的名称
         */
        String sheetName;
        /**
         * 表格标题
         */
        String title;
        /**
         * 头部标题集合
         */
        String[] headers;
        /**
         * 数据集合
         */
        List<List<Object>> dataList;
    }


    public enum ExcelVersion {

        /**
         * 虽然V2007版本支持最大支持1048575 * 16383 ，
         * V2003版支持65535*255
         * 但是在实际应用中如果使用如此庞大的对象集合会导致内存溢出，
         * 因此这里限制最大为10000*100，如果还要加大建议先通过单元测试进行性能测试。
         * 1000*100 全部导出预计时间为27s左右
         */
        V2003(".xls", 10000, 100), V2007(".xlsx", 100, 100);

        String suffix;

        int maxRow;

        int maxColumn;

        ExcelVersion(String suffix, int maxRow, int maxColumn) {
            this.suffix = suffix;
            this.maxRow = maxRow;
            this.maxColumn = maxColumn;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public int getMaxRow() {
            return maxRow;
        }

        public void setMaxRow(int maxRow) {
            this.maxRow = maxRow;
        }

        public int getMaxColumn() {
            return maxColumn;
        }

        public void setMaxColumn(int maxColumn) {
            this.maxColumn = maxColumn;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

    }
}
