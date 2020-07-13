import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.sql.DriverManager;
import java.io.*;

class MySqlToXml {
    static final String url = "jdbc:mysql://localhost:3306/ce126";

    public static void dbToXml(String xmlFile, String jsonFile) {

        Connection con = null;
        Statement st = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connecting
            System.out.println("Connectiong...");
            con = DriverManager.getConnection(url, "root", "");
            // creating statement
            st = con.createStatement();
            String sql = "Show Full Tables;";
            ResultSet rs = st.executeQuery(sql);
            ResultSet rs2 = null;
            while (rs.next()) {
                String table = rs.getString(1);
                stmt = con.createStatement();
                rs2 = stmt.executeQuery("Select * FROM " + table + " XML AUTO;");
                while (rs2.next()) {
                    System.out.println(rs2.getString(1));
                }
                // System.out.println(data);

                // System.out.println(table);
            }
            rs.close();
            con.close();
        } catch (Exception se) {
            System.out.println(se.toString());
        }

    }

    public static void main(String[] args) {

        try {
            dbToXml("M:/M_stud/SEM-VII/BDA/Lab1/t1.xml", "M:/M_stud/SEM-VII/BDA/Lab1/t2.json");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}