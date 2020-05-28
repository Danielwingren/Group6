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
                seeBookedClasses(memberID, tier, fnamn, uname, defaultGym);
                break;
            case 2 :
                viewClassInformation();
                break;
            case 3 :
                membershipSystem.accountInformation(memberID, tier, uname, fnamn, defaultGym);
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
        int todayx = 0;
        int yesterdayx = -1;
        int tomorrowx = +1;

        while (true) {
            String todayy;
            // Real date to present for buttons
            Date realDate = new Date();
            SimpleDateFormat srdf = new SimpleDateFormat(" E dd/MM/yyyy");
            Calendar cr = Calendar.getInstance();
            cr.setTime(realDate);
            cr.add(Calendar.DATE, todayx);
            realDate = cr.getTime();
            String realtoday = (srdf.format(realDate));

            Date dt1 = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt1);
            c.add(Calendar.DATE, yesterdayx);
            dt1 = c.getTime();
            String yesterday = (srdf.format(dt1));

            Date dt2 = new Date();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(dt2);
            c1.add(Calendar.DATE, tomorrowx);
            dt2 = c1.getTime();
            String tomorrow = (srdf.format(dt2));

            //Date in format of SQL database
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            todayy = (sdf.format(realDate));
            String message = sql.ViewAllClasses(todayy, defaultGym);
            String result = sql.AvailableClasses(todayy,defaultGym);

            JFrame frame = new JFrame();
            new JTextArea();
            String[] options = new String[3];
            options[0] = yesterday;
            options[1] = "OK";
            options[2] = tomorrow;
            int val = JOptionPane.showOptionDialog(frame.getContentPane(), message,
                    "Classes for: " + realtoday + " on gym: " +defaultGym, 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
            if (val == 0) {
                todayx = todayx - 1;
                yesterdayx = yesterdayx - 1;
                tomorrowx = yesterdayx - 1;
            }
            else if (val == 2) {
                todayx = todayx + 1;
                yesterdayx = yesterdayx + 1;
                tomorrowx = yesterdayx + 1;
            }
            else if (val ==CLOSED_OPTION ) {
                break;
            }
            else if (val == 1) {
                int choice = showConfirmDialog(null,"Do you wish to book an available class for this date?","Menu",YES_NO_OPTION,PLAIN_MESSAGE);
                if (choice == YES_OPTION ) {
                    bookClass(memberID, result, tier, fnamn, uname, defaultGym);
                    break;
                }
                else {
                    classbooking.memberscreen(memberID, tier, fnamn, uname, defaultGym);
                }
            }
        }
        classbooking.memberscreen(memberID, tier, fnamn, uname, defaultGym);
    }
    public static void seeBookedClasses (String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
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

    public static void bookClass (String memberID, String result, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String classesx = "classID |\t  classname  |\t  time  |\t  date |\t  roomID   |\t  Instructor\n";
        String timeOfEnroll = (formatter.format(date));
        String classID = showInputDialog(result +"Please enter the classID of the class you wish to book yourself to.","enter classID here");

        String sqlconfirm = "select class.classID, class.className, class.time, class.date, class.availableSlots, room.roomID, gym.location, member.fName, member.lName from class natural join instructor natural join room natural join gym join member on member.memberID=instructor.memberID where class.classID = '"+classID+"';";
        ResultSet rs = sql.confirmClass(sqlconfirm);
        if (rs == null) {
            showMessageDialog(null,"Could no find the class");
        }
        else
        while (rs.next()) {
            String classIDx = rs.getString("classID");
            String classname = rs.getString("classname");
            String time = rs.getString("time");
            String datex = rs.getString("date");
            int roomID = rs.getInt("roomID");
            String fName = rs.getString("fName");
            String lName = rs.getString("lName");
            String classes = (classIDx + " |\t " + classname + " |\t " + time + " |\t " + datex + " |\t " + roomID + " |\t " + fName + " " + lName + "\n");
            classesx = classesx + classes;
        }
        String sqlbookclass = "insert into memberClass (\"classID\", \"memberID\", \"timeOfEnroll\") VALUES ('"+classID+"','"+memberID+"','"+timeOfEnroll+"')";
        if (fullClass(classID)) { //if its true
            int val = showOptionDialog(null,"The class does not currently have any free slots, do you wish to place yourself in queue?","FULL CLASS",YES_NO_OPTION,PLAIN_MESSAGE,null,null,null);
            if (val == YES_OPTION) {
                sql.bookClass(sqlbookclass);
                showConfirmDialog(null,"You are now placed in que, you will recieve a notification if a slot opens.","Message",DEFAULT_OPTION,PLAIN_MESSAGE);
                classbooking.memberscreen(memberID, tier, fnamn, uname, defaultGym);
            }
            else if (val == NO_OPTION) {
                showConfirmDialog(null,"Sending you back to available classes","Message",DEFAULT_OPTION,PLAIN_MESSAGE);
                classbooking.seeClasses(memberID, tier, fnamn, uname, defaultGym);
            }
        }
        else {
            int val = showConfirmDialog(null,"Do you wish to confirm a reservation for the class below?\n"+classesx,"Confirmation",YES_NO_OPTION,PLAIN_MESSAGE);
            if (val== YES_OPTION) {
                sql.bookClass(sqlbookclass);
            }
            else {
                classbooking.memberscreen(memberID, tier, fnamn, uname, defaultGym);
            }
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
    public static boolean fullClass (String classID) throws SQLException {

        String query = "select class.classID, class.availableSlots, count (memberClass.memberID) from class natural join memberClass where class.classID = '"+classID+"'";
        ResultSet rs = sql.checkFull(query);
        int available = 0;
        int booked = 0;
        try {
            while (rs.next()) {
            available = rs.getInt(0);
            booked = rs.getInt(1);
            }
        }
        catch (NullPointerException e) {
            showMessageDialog(null,"Error checking availability for class");
        }
        finally {
            rs.close();
        }
        if (available <= booked) {
            return true;
        }
        else {
            return false;
        }
    }
}
