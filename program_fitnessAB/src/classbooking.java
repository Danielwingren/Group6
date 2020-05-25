import javax.swing.*;

import org.sqlite.SQLiteConfig;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class classbooking {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void memberscreen(String memberID, int tier, String fnamn, String uname) throws SQLException {

        JFrame frame = new JFrame();
        String[] options = new String[5];
        options[0] = "See all classes";
        options[1] = "See booked classes";
        options[2] = "See information about classes";
        options[3] = "Update Account information";
        options[4] = "Log out";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ". What operation would you like to perform?\n", "Main menu ", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        // Sqlite query som hämtar membership-nivå och visar ängst upp instället för "member" ??
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0:
                seeClasses();
                break;
            case 1:
                seeBookedClasses(memberID);
                break;

            case 2:
                viewClassInformation();
                break;

            case 3:
                membershipSystem.UpdateInformation(memberID, tier, uname, fnamn);
                break;
            case 4:
                fitnessAB.login();

        }
    }

    public static void manageClasses() {
    }

    public static void seeClasses() throws SQLException {
        ResultSet rs = sql.ViewAllClasses();
        JOptionPane.showMessageDialog(null, rs);
        StringBuilder str = new StringBuilder();
        String Class = rs.getString("className");
        JOptionPane.showMessageDialog(null, Class);
        while (rs.next()) { //här hämtar den in data för varje kolumn
            str.append("Name:" + rs.getString("className"));
            str.append("Start time:" + rs.getString("time"));
            str.append("Date: " + rs.getString("date"));
            str.append("Room: " + rs.getInt("roomID"));
            str.append("Intructor firstname: " + rs.getString("fName"));
            str.append("Intructor lastname: " + rs.getString("lName"));
        }
        String resultat = (str.toString());
        JOptionPane.showMessageDialog(null, (str.toString()) + "här är strängen --> " + resultat);
    }

    public static void seeBookedClasses(String memberID) throws SQLException {
        ResultSet rs = sql.getBookedClasses(memberID);
        JOptionPane.showMessageDialog(null, rs);
        StringBuilder str = new StringBuilder();
        while (rs.next()) { //här hämtar den in data för varje kolumn
            str.append("Name:").append(rs.getString("class.className"));
            str.append("Start time:").append(rs.getString("class.time"));
            str.append("Date: ").append(rs.getString("class.date"));
            str.append("Room: ").append(rs.getInt("class.roomID"));
            str.append("Intructor firstname: ").append(rs.getString("member.fname"));
            str.append("Intructor lastname: ").append(rs.getString("member.lname"));
        }
    }

    public static void bookClass(String memberID) {
        int x = 5;
        String class1 = "";
        JPanel bookclasspanel = new JPanel();
        bookclasspanel.setLayout(new GridLayout(10, 1));

        for (int i = 0; i <= x; i++) {
            JLabel info = new JLabel("Class: " + class1);

        }

    }

    public static void viewClassInformation() throws SQLException {
        //Choose classtypes
        ResultSet rs = sql.GetClassName();
        //ResultSet rs = classtype;

        try {
            while (rs.next()) {
                showMessageDialog(null, rs.getString(0) + " " + rs.getString(1) + "\n");
            }
        } catch (SQLException e){
                showMessageDialog(null, e);
            }


            //Information about classes, fetch description and name
        }

    }

