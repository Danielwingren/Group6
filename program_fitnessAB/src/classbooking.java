import javax.swing.*;

import org.sqlite.SQLiteConfig;
import java.text.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import static javax.swing.JOptionPane.*;

public class classbooking {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void memberscreen (String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        System.out.println("Gym: " + defaultGym);
        JFrame frame = new JFrame();
        String[] options = new String[6];
        options[0] = "See all classes";
        options[1] = "See booked classes";
        options[2] = "Information about classes";
        options[3] = "Account information";
        options [5] = "Log out";
        options [4] = "Change location";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome "+fnamn+". What operation would you like to perform?\nYour selected location is: " + defaultGym, "Main menu ", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        // Sqlite query som hämtar membership-nivå och visar ängst upp instället för "member" ??
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0 :
                seeClasses(memberID, tier, fnamn, uname, defaultGym);
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
            case 5 :
                fitnessAB.login();
            case 4 :
                classbooking.changelocation(memberID, tier, uname, fnamn, defaultGym);

        }
    }

    private static void changelocation(String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        Object[] options = {"Hisingen", "Bergsjön", "Långedrag"};

        Object selectionObject = JOptionPane.showInputDialog(frame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        String selectedGym = selectionObject.toString();
        defaultGym = selectedGym;
        classbooking.memberscreen(memberID, tier, uname, fnamn, defaultGym);
    }



    public static void seeClasses (String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {

        // Real date to present for buttons
        Date realDate = new Date();
        SimpleDateFormat srdf = new SimpleDateFormat(" E dd/MM/yyyy");
        String realtoday = (srdf.format(realDate));

        Date dt1 = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt1);
        c.add(Calendar.DATE, -1);
        dt1 = c.getTime();
        String yesterday = (srdf.format(dt1));

        Date dt2 = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dt2);
        c1.add(Calendar.DATE, 1);
        dt2 = c1.getTime();
        String tomorrow = (srdf.format(dt2));

        //Date in format of SQL database
        Date sqldate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = (sdf.format(sqldate));
        String message = sql.ViewAllClasses(today,defaultGym);

            JFrame frame = new JFrame();
            new JTextArea();
            String[] options = new String[3];
            options[0] = yesterday;
            options[1] = "OK";
            options[2] = tomorrow;
            int val = JOptionPane.showOptionDialog(frame.getContentPane(),message,
                    "Classes for: " +realtoday, 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
            if (val == 0) {
                dt1 = new Date();
                c = Calendar.getInstance();
                c.setTime(dt1);
                c.add(Calendar.DATE, -1);
                dt1 = c.getTime();
                today = (sdf.format(dt1));

            }
        classbooking.memberscreen(memberID, tier, fnamn, uname, defaultGym);
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
