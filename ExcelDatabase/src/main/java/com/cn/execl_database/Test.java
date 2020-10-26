package com.cn.execl_database;

import com.cn.utils.JDBCUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

/**
 * @Author:zhangyang
 * @Date: 2020-7-13 10:59
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        Connection conn = null ;
        PreparedStatement stmt = null ;
        ResultSet rs = null ;

        try {
            //获取数据库连接
            conn = JDBCUtils.getConnection();
           //准备sql语句
            String sql = "select * from account" ;
           stmt =  conn.prepareStatement(sql) ;
            rs = stmt.executeQuery();
            //创建工作表 XSSFWorkbook对象
            XSSFWorkbook workbook = new XSSFWorkbook() ;
            //定义工作表,指定名称
            XSSFSheet xssfSheet = workbook.createSheet("account");
            XSSFRow row  = xssfSheet.createRow(0);//创建第一行
            XSSFCell cell ; //定义单元格

            // 获取列名 resultSet数据下标从1开始
            ResultSetMetaData metaData = rs.getMetaData();//获取元数据列
            for(int i = 0 ;i < metaData.getColumnCount(); i ++){
                int index = i + 1;
                String columnName = metaData.getColumnName(index);
                cell = row.createCell(1);
                cell.getCellStyle().setWrapText(true);
                cell.setCellValue(columnName);
            }

            int i=1;
            while(rs.next())
            {
                row=xssfSheet.createRow(i);
                for (int j = 0; j< metaData.getColumnCount(); j++) {
                    int index=j+1;
                    cell=row.createCell(j);
                /*
                XSSFCellStyle cellStyle=workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);
                */
                    cell.setCellValue(rs.getString(index));
                }
                i++;
            }

            FileOutputStream out = new FileOutputStream(new File("excel_database.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("excel_database.xlsx written successfully");

        } catch (SQLException e) {



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
