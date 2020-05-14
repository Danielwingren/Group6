import javax.swing.*;

import org.sqlite.SQLiteConfig;
import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class classbooking {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void employeeScreen (String namn) {

        JFrame frame = new JFrame();
        String[] options = new String[3];
        options[0] = "Manage classes";
        options[1] = "See all classes";
        options[2] = "See booked classes";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "What operation would you like to perform?", "Membership (Admin)", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
    }
    public static void memberscreen (String namn) {

        JFrame frame = new JFrame();
        String[] options = new String[2];
        options[0] = "See all classes";
        options[1] = "See booked classes";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Would you like to view classes or see already booked classes?", "Member ", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        // Sqlite query som hämtar membership-nivå och visar ängst upp instället för "member" ??
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }

    }

































    //-------------------------Danne jobbar ovan ^
    //------------------------ Emils workspace nedan:







}
