package ru.avalon.java.j30.labs;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Main {
    
    static Connection conn = null;
    
    public static void main(String[] args) {
        
        try {
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/sample", "app", "app");
        } catch (SQLException ex) {
            System.out.println("ошибка соединения!" + ex.getMessage());
        }
        System.out.println("соединение " + conn);
        runSelect("select * from product");
    }
    
    private static void runSelect(String query) {
        try (Statement st = conn.createStatement()){
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNums = rsmd.getColumnCount();
            
            while(rs.next()) {
                for (int i = 1; i <= columnNums; i++) {
                    switch (rsmd.getColumnTypeName(i)){
                        case "CHAR":
                        case "VARCHAR": System.out.print(rs.getString(i) + " "); break;
                        case "INTEGER": System.out.print(rs.getInt(i) + " "); break;
                        case "DECIMAL":
                        case "BOOLEAN": System.out.print(rs.getBoolean(i) + " "); break;
                        case "DOUBLE": System.out.print(rs.getDouble(i) + " "); break;
                        default: System.out.print(rsmd.getColumnTypeName(i) + " ");
                    }
                } 
                    System.out.println("");
            }
        } catch (SQLException ex) {
            System.out.println("ошибка в SQL" + ex.getMessage());     
}
    }
    
}
