import javax.swing.*;

import org.sqlite.SQLiteConfig;
import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class classbooking {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void memberscreen (String memberID, int tier, String fnamn, String uname) throws SQLException {

        JFrame frame = new JFrame();
        String[] options = new String[3];
        options[0] = "See all classes";
        options[1] = "See booked classes";
        options[2] = "Update Account information";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome "+fnamn+". Would you like to view classes or see already booked classes?\n", "Member ", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        // Sqlite query som hämtar membership-nivå och visar ängst upp instället för "member" ??
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0 :
                seeClasses();
                break;
            case 1 :
                seeBookedClasses(memberID);

            case 2 :
                membershipSystem.UpdateInformation(memberID, tier, uname, fnamn);

        }
    }
    public static void manageClasses (){}

    public static void seeClasses () throws SQLException {
        ResultSet rs = sql.ViewAllClasses();
        JOptionPane.showMessageDialog(null,rs);
        StringBuilder str = new StringBuilder();
        while(rs.next()){ //här hämtar den in data för varje kolumn
            str.append("Name:").append(rs.getString("className"));
            str.append("Start time:").append(rs.getInt("time"));
            str.append("Date: ").append(rs.getInt("date"));
            str.append("Room: ").append(rs.getInt("roomID"));
            str.append("Intructor: ").append(rs.getString("name"));
            str.append("\n");
        }
        String resultat = (str.toString());
        JOptionPane.showMessageDialog(null,(str.toString())+"här är strängen --> " + resultat);
    }
    public static void seeBookedClasses (String memberID) throws SQLException {
        ResultSet rs = sql.getBookedClasses(memberID);
        JOptionPane.showMessageDialog(null,rs);
        StringBuilder str = new StringBuilder();
        while(rs.next()){ //här hämtar den in data för varje kolumn
            str.append("Name:\t " + rs.getString("className")+ "\t, ");
            str.append("\tStart time:" + rs.getInt("time") + "\t, ");
            str.append("\tDate: " + rs.getInt("date") + "\t, ");
            str.append("\tRoom: " + rs.getInt("roomID") + "\t, ");
            str.append("\tIntructor: " + rs.getString("fName") + "\t, ");
            str.append("\n");
        }
    }







































}
