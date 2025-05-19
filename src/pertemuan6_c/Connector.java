package pertemuan6_c;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static java.awt.image.ImageObserver.HEIGHT;

public class Connector {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/movie_db"; // ganti ke database tugas
    static final String USER = "root";
    static final String PASS = "";

    public Connection koneksi;
    public Statement statement;

    public Connector() {
        try {
            Class.forName(JDBC_DRIVER);
            koneksi = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Koneksi Berhasil");
        } catch(Exception ex) {
            System.out.println("Koneksi Gagal: " + ex.getMessage());
        }
    }

    public boolean tambahData(String Judul, double Alur, double Penokohan, double Akting) throws SQLException {
        try {
            if(Judul.isEmpty()) throw new Exception("Judul Kosong");
            if(Alur < 0 || Alur > 5) throw new Exception("Nilai Alur harus 0-5");
            if(Penokohan < 0 || Penokohan > 5) throw new Exception("Nilai Penokohan harus 0-5");
            if(Akting < 0 || Akting > 5) throw new Exception("Nilai Akting harus 0-5");

            double Nilai = (Alur + Penokohan + Akting) / 3;

            String query = "INSERT INTO movie_db (Judul, Alur, Penokohan, Akting, Nilai) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, Judul);
            ps.setDouble(2, Alur);
            ps.setDouble(3, Penokohan);
            ps.setDouble(4, Akting);
            ps.setDouble(5, Nilai);
            ps.executeUpdate();
            return true;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Alert", HEIGHT);
            return false;
        }
    }

    public boolean deleteData(int id) {
        try {
            String query = "DELETE FROM movie_db WHERE id = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editData(int id, String Judul, double Alur, double Penokohan, double Akting) {
        try {
            double Nilai = (Alur + Penokohan + Akting) / 3;
            String query = "UPDATE movie_db SET Judul = ?, Alur = ?, Penokohan = ?, Akting = ?, Nilai = ? WHERE id = ?";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, Judul);
            ps.setDouble(2, Alur);
            ps.setDouble(3, Penokohan);
            ps.setDouble(4, Akting);
            ps.setDouble(5, Nilai);
            ps.setInt(6, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean clearAllData() {
        try {
            String query = "DELETE FROM movie_db";
            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}