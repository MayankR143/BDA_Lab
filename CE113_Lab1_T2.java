import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.sql.DriverManager;
import java.io.*;

class CsvToMySql {
    static final String url = "jdbc:mysql://localhost:3306/ce126";

    public static void csvToDb(String csv) {

        String line = "";
        String csvSplitter = ",";
        Connection con = null;
        Statement st = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connecting
            System.out.println("Connectiong...");
            con = DriverManager.getConnection(url, "root", "");
            // creating statement
            st = con.createStatement();

            // String sql = "CREATE TABLE test_bda(id INT(11) AUTO_INCREMENT, `title`
            // VARCHAR(255) NOT NULL, `expired_date` DATE NOT NULL, `amount` DECIMAL(10,2)
            // NOT NULL, PRIMARY KEY (id));";
            // st.executeUpdate(sql);
            // LOAD DATA INFILE '?' INTO TABLE ? FIELDS TERMINATED BY ',' ENCLOSED BY
            // '\"'LINES TERMINATED BY '\\n'IGNORE 1 ROWS;
            // Loads data from directly csv
            String sql = "LOAD DATA INFILE '" + csv
                    + "' INTO TABLE test_bda FIELDS TERMINATED BY ',' LINES TERMINATED BY '\\n'IGNORE 1 ROWS;";
            st.executeUpdate(sql);
            ResultSet rs = st.executeQuery("SELECT * FROM test_bda;");
            /*
             * while ((line = br.readLine()) != null) { String[] cells =
             * line.split(csvSplitter); for (String str : cells) { System.out.print(str +
             * ", "); } System.out.println(); }
             */
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ", " + rs.getString("title") + ", "
                        + rs.getDate("expired_date").toString() + ", " + rs.getDouble("amount"));
            }
            rs.close();
            st.close();
            con.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (Exception se) {
            System.out.println(se.toString());
        }

    }

    public static void main(String[] args) {
        String csvPath = "M:/M_stud/SEM-VII/BDA/Lab1/f1.csv";
        try {
            csvToDb(csvPath);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}