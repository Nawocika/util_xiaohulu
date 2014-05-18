package test;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by lw on 14-5-7.
 */
public class Test2 {
    private String driver;
    private String url;
    private String user;
    private String pass;

    public void initParam(String fileParam) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream("a.ini"));
        driver = props.getProperty("driver");
        url = props.getProperty("url");
        user = props.getProperty("user");
        pass = props.getProperty("pass");
        Class.forName(driver);
    }

    public void createConnecting(String sql) throws Exception {
//String sql = "insert into table fs.friens(sno,sname,sage,Sdept) value(?,?,?,?);";
        Connection conn = DriverManager.getConnection(url, user, pass);
        PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//pstmt.executeUpdate(sql);//可执行DDL和DML语句。
        ResultSet rs = pstmt.getResultSet();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please put in how much data in this?");
        int n;
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int sno;
            sno = scanner.nextInt();
            pstmt.setInt(1, sno);
            String sname;
            sname = scanner.next();
            pstmt.setString(2, sname);
            int sage;
            sage = scanner.nextInt();
            pstmt.setInt(3, sage);
            String sdept;
            sdept = scanner.next();
            pstmt.setString(4, sdept);
//pstmt.executeUpdate();
        }
        ResultSetMetaData meta = rs.getMetaData();
        int column = meta.getColumnCount();
        int rowCount = meta.getColumnCount();
        System.out.print(meta.getColumnName(1));
        System.out.print(" ");
        System.out.print(meta.getColumnName(2));
        System.out.print(" ");
        System.out.print(meta.getColumnName(3));
        System.out.print(" ");
        System.out.print(meta.getColumnName(4));
        if (rs.isAfterLast()) {
            for (int i = 0; i < rowCount; i++) {
                System.out.print(rs.getObject(1));
                System.out.print(" ");
                System.out.print(rs.getObject(2));
                System.out.print(" ");
                System.out.print(rs.getObject(3));
                System.out.print(" ");
                System.out.print(rs.getObject(4));
                System.out.print(" ");

            }
        }
    }

    public static void main(String[] args) throws Exception {
        String sql = "insert into fs.friends(sno,sname,sage,Sdept) values(?,?,?,?);";
//Scanner sc = new Scanner(System.in);
//sql = sc.next();
        Test2 fsCrazy = new Test2();
        fsCrazy.initParam("a.ini");
        fsCrazy.createConnecting(sql);
    }
}