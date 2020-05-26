import javax.swing.*;

import org.sqlite.SQLiteConfig;

import java.awt.*;
import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class classbooking {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void memberscreen (String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {

        JFrame frame = new JFrame();
        String[] options = new String[5];
        options[0] = "See all classes";
        options[1] = "See booked classes";
        options[2] = "Information about classes";
        options[3] = "Account information";
        options [4] = "Log out";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome "+fnamn+". What operation would you like to perform?\nYour selected location is: " + defaultGym, "Main menu ", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
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
                break;
            case 2 :
                viewClassInformation();
                break;
            case 3 :
                membershipSystem.UpdateInformation(memberID, tier, uname, fnamn, defaultGym);
                break;
            case 4 :
                fitnessAB.login();

        }
    }

    public static void seeClasses () throws SQLException {
        conn = sql.dbconnection();
        String classes = "";
        String classesx = "";
        Statement stmt = null;
        String query = "select class.className, class.time, class.date, class.roomID , member.fName, member.lName from member join instructor on member.memberID = instructor.memberID natural join class";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String classname = rs.getString("classname");
                String time = rs.getString("time");
                String date = rs.getString("date");
                int roomID = rs.getInt("roomID");
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                classes = (classname  + " \t " + time + " \t " + date + " \t " + roomID + " \t " + fName + " " + lName +"\n");
                classesx = classesx + classes;
            }
            JOptionPane.showMessageDialog(null,new JTextArea(classesx));

        } catch (SQLException e) {
            showMessageDialog(null, "Fel din idjut");
            System.out.println(e.toString());
        } finally {
            if (stmt != null) { stmt.close(); }
            conn.close();
        }
        /*ResultSet rs = sql.ViewAllClasses();
        JOptionPane.showMessageDialog(null,rs);
        StringBuilder str = new StringBuilder();
        while(rs.next()){ //här hämtar den in data för varje kolumn
            str.append("Name:").append(rs.getString("className"));
            str.append("Start time:").append(rs.getString("time"));
            str.append("Date: ").append(rs.getString("date"));
            str.append("Room: ").append(rs.getInt("roomID"));
            str.append("Intructor firstname: ").append(rs.getString("fName"));
            str.append("Intructor lastname: ").append(rs.getString("lName"));
        }
        String resultat = (str.toString());
        JOptionPane.showMessageDialog(null, (str.toString())+"Här är resultratet av string: " + resultat); */
        //memberscreen(); Varför bråkar det och vill inte gå tilblaka till memberscreen????? ?? ? ? ? Danne pls
    }
    public static void seeBookedClasses (String memberID) throws SQLException {
        ResultSet rs = sql.getBookedClasses(memberID);
        JOptionPane.showMessageDialog(null,rs);
        StringBuilder str = new StringBuilder();
        while(rs.next()){ //här hämtar den in data för varje kolumn
            str.append("Name:").append(rs.getString("class.className"));
            str.append("Start time:").append(rs.getString("class.time"));
            str.append("Date: ").append(rs.getString("class.date"));
            str.append("Room: ").append(rs.getInt("class.roomID"));
            str.append("Intructor firstname: ").append(rs.getString("member.fname"));
            str.append("Intructor lastname: ").append(rs.getString("member.lname"));
        }
        String resultat = (str.toString());
        JOptionPane.showMessageDialog(null, (str.toString())+"Här är resultratet av string: " + resultat);
    }

    public static void bookClass (String memberID) {
        int x = 5;
        String class1 = "";
        JPanel bookclasspanel = new JPanel();
        bookclasspanel.setLayout(new GridLayout(10,1));

        for (int i = 0; i <= x; i++) {
            JLabel info = new JLabel("Class: "+class1);

        }

    }
    public static void viewClassInformation() throws SQLException {
        //Choose classname, click button

        conn = sql.dbconnection();
        String query = "select distinct classname, description from classtype;";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        String description = "";
        String classname = "";
        String classes = "";
        try {
            while (rs.next()) {
                classname = rs.getString("classname");
                description = rs.getString("description");
                classes = classes + "\n" + classname + "\n" + description + "\n";
            }
            showMessageDialog(null, classes);
        } catch (SQLException e) {
                showMessageDialog(null, "Fel!");
                System.out.println(e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            rs.close();
            conn.close();
            }

        //Information about classes, fetch description and name

    }
}
