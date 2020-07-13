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
            FileOutputStream ops = new FileOutputStream(new File(xmlFile));
            FileOutputStream ops1 = new FileOutputStream(new File(jsonFile));
            StringBuffer data = new StringBuffer();
            StringBuffer data1 = new StringBuffer();
            ResultSet rs2 = null;
            data1.append("[\n{\"type\":\"database\",\"name\":\"ce126\" },");
            data.append("<database name=\"ce126\">\n");
            while (rs.next()) {
                stmt = con.createStatement();
                String table = rs.getString(1);
                System.out.println(table);
                data1.append("{\"type\":\"table\",\"name\":\"" + table + "\",\"database\":\"ce126\",\n");
                rs2 = stmt.executeQuery("Select * from " + table + ";");
                ResultSetMetaData rem = rs2.getMetaData();
                int cols = rem.getColumnCount();
                data.append("<columns name=\"" + table + "\">\n");
                data1.append("\"cols\":[\n");
                for (int i = 1; i <= cols; i++) {
                    String name = rem.getColumnName(i);
                    int type = rem.getColumnType(i);
                    data.append("<col>\n<name>" + name + "</name>\n<type>" + type + "</type>\n</col>\n");
                    data1.append("{name:\"" + name + "\",\"type\":" + type + " },\n");
                }
                data.append("</columns>");
                data1.append("],\n\"data\":[");
                // System.out.println(data);
                while (rs2.next()) {
                    data.append("<table name=\"" + table + "\">\n");
                    data1.append('{');
                    for (int i = 1; i <= cols; i++) {
                        String name = rem.getColumnName(i);
                        String content = rs2.getString(i);
                        data.append("<column name=\"" + name + "\">" + content + "</column>\n");
                        data1.append("\"" + name + "\":\"" + content + "\",");
                    }
                    data.append("\n</table>\n");
                    data1.append(" },\n");
                }
                data1.append("]\n},");
                // System.out.println(data);
                rs2.close();
                stmt.close();
                // System.out.println(table);
            }
            data1.append(']');
            ops.write(data.toString().getBytes());
            ops1.write(data1.toString().getBytes());
            ops.close();
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