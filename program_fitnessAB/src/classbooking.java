import javax.swing.*;

import org.sqlite.SQLiteConfig;
import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class classbooking {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void employeeScreen (String namn) {

        JOptionPane.showMessageDialog(null, "Welcome "+namn+".\nWhat operation would you like to perform?\n" +
                                                                      "(Switch method, enter the letter corresponding to the action performed in the UI");



    }
    public static void memberscreen (String namn) {
        JOptionPane.showMessageDialog(null, "Welcome "+namn+".\nWhat operation would you like to perform?\n" +
                "(Switch method, enter the letter corresponding to the action performed in the UI");

    }

































    //-------------------------Danne jobbar ovan ^
    //------------------------ Emils workspace nedan:







}
