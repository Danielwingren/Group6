import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.JOptionPane.*;
import org.sqlite.SQLiteConfig;
import javax.swing.*;

public class sql {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static Connection dbconnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(DB_URL, config.toProperties());
            return conn;
        } catch (Exception e) {
            // Om den inte lyckas skapa en anslutning till databasen så bör vi få ett felmeddelande
            e.printStackTrace();
            System.exit(0);
        }
        return conn;
    }
    public static String login(String uname) throws SQLException {
        conn = dbconnection();
        String error = "-";
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            String sql = ("select email from member where email = '" + uname.toLowerCase() + "';");
            rs = st.executeQuery(sql);
            String user = rs.getString("email");
            return user;
        } catch (SQLException e) {
            showMessageDialog(null, "Could not find that user, please try again or\nvisit one of our facilitites to register a new membership.\n \nKind Regards\nFitness AB");
            e.printStackTrace();
        }
        finally {
        conn.close();
    }
        return error;
    }
    public static String GetPassword(String uname) throws SQLException {
        conn = dbconnection();
        String error = "-";
        ResultSet rs = null;
        try {
            String sqlpw = ("select loginpw from member where email = '" + uname + "';");
            rs = conn.createStatement().executeQuery(sqlpw);
            return rs.getString("loginpw");
        } catch (SQLException e) {
            System.out.println("All works perfectly!!");
        } finally {
            conn.close();
        }
        return error;
    }
    public static String getName(String username) throws SQLException {
        conn = dbconnection();
        String error = "";
        ResultSet rs = null;
        try {
            String sqlname = ("select fName, lName from member where email = '" + username + "';"); //-
            rs = conn.createStatement().executeQuery(sqlname);                           //- - Dessa tre rader tar fram namnet på den inloggade medlemmen
            return rs.getString("fName");
        }
        catch (SQLException e) {
            showMessageDialog(null, "Could not load name of user");
            e.printStackTrace();
        }
        finally {
            assert rs != null;
            rs.close();
            conn.close();
        }
        return error;
    }
    public static int GetTier(String username) throws SQLException {
        conn = dbconnection();
        int error = 666;
        ResultSet rs1 = null;
        try {
            String sqlReadTier = ("select tierType from member where email = '" + username + "';");    // -
            rs1 = conn.createStatement().executeQuery(sqlReadTier);                         // -
            String tierType = rs1.getString("tiertype");// - Dessa fyra rader läser av ifall det är en anställd eller ej (läser in tiertype)
            System.out.println("Tiertype = "+tierType);
            int tier = Integer.parseInt(tierType);
            return tier;
        }
        catch (SQLException e) {
            System.out.println("Could not load tier");
            e.printStackTrace();
        }
        finally {
            conn.close();
        }
        return error;
    }
    public static String getHomeGym(String memberID) throws SQLException {
        ResultSet rs2 = null;
        String error = "";
        conn = dbconnection();
        try {
            String sqlGymNo = ("select location from gym join member on member.defaultGym = gym.gymID where member.memberID ='" + memberID + "';");     // -
            rs2 = conn.createStatement().executeQuery(sqlGymNo);                         // - Dessa tre rader läser in hemmagym
            return rs2.getString(1);
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting location name");
            e.printStackTrace();
        } finally {
            rs2.close();
            conn.close();
        }
        return error;
    }
    public static String GetMemberID(String username) throws SQLException {
        ResultSet rs2 = null;
        String error = "-";
        conn = dbconnection();
        try {
            String sqlReadMemberID = ("select memberID from member where email ='" + username + "';");     // -
            rs2 = conn.createStatement().executeQuery(sqlReadMemberID);                         // - Dessa tre rader läser in medlemsID
            return rs2.getString("memberID");
        }
        catch (SQLException e) {
            showMessageDialog(null, "Error getting memberID");
            e.printStackTrace();
        }
        finally {
            rs2.close();
            conn.close();
        }
        conn.close();
        return error;
    }
    public static String GetPaymentDate(String memberID) throws SQLException {
        ResultSet rs = null;
        String error = "-";
        try {
            conn = dbconnection();
            String query = ("select date from payments where memberID ='" + memberID + "';");
            rs = conn.createStatement().executeQuery(query);
            return rs.getString("date");
        }
        catch (SQLException e) {
            System.out.println("Error getting date");
            e.printStackTrace();
        }
        finally {
            rs.close();
            conn.close();
        }
        return error;
    }
    public static String GetPaymentAmount(String memberID) throws SQLException {
        ResultSet rs = null;
        String error = "-";
        try {
            conn = dbconnection();
            String query = ("select amount from payments where memberID =" + memberID + ";");
            rs = conn.createStatement().executeQuery(query);
            return rs.getString("amount");
        }
        catch (SQLException e) {
            showMessageDialog(null, "Could not find any transactions");
            e.printStackTrace();
        }
        finally {
            rs.close();
            conn.close();
        }
        return error;
    }
    public static void ChangePassword(String uname, String newPw) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
        conn.setAutoCommit(false);
        stmt = conn.createStatement();
        String sql = ("update member set loginpw = '" + newPw + "'where email = '"+ uname +"';");
        stmt.executeUpdate(sql);
        conn.commit();
        String SQL = "select loginpw from member where email = '"+uname+"';";
        ResultSet rs = stmt.executeQuery(SQL);
        showMessageDialog(null,"Your password is updated.");
        stmt.close();
        conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        fitnessAB.login();
    }
    public static String ViewAllClasses (String today, String defaultGym, String type) throws SQLException {
        conn = sql.dbconnection();
        String classes;
        String classesx = "";
        String message = "Class ID | Class name | \t Time |\t Date |\t Room Nr |\t Instructor |\n";
        Statement stmt = null;
        String query = "select class.classID, class.className, class.time, class.date, class.availableSlots, room.roomID, gym.location, member.fName, member.lName from class natural join instructor natural join room natural join gym NATURAL join classType "+
        "join member on member.memberID=instructor.memberID where class.date = '"+today+"' AND classType.classType like '"+type+"' AND room.roomID in (select room.roomID from gym natural join room where gym.location='"+defaultGym+"')";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("query funkar");
            while (rs.next()) {
                String classID = rs.getString("classID");
                String classname = rs.getString("classname");
                String time = rs.getString("time");
                String date = rs.getString("date");
                int roomID = rs.getInt("roomID");
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                classes = (classID + " |\t "+ classname + " |\t " + time + " |\t " + date + " |\t " + roomID + " |\t " + fName + " " + lName + "\n");
                classesx = classesx + classes;
            }
            message = message + classesx + ("\nAbove you see the classes available for today, to see for other please press next day");
        }
        catch (SQLException e) {
        showMessageDialog(null, "Error while reading classes");
        System.out.println(e.toString());
        }
        finally {
        if (stmt != null) { stmt.close(); }
        conn.close();
        }
        return message;
    }
    public static String AvailableClasses (String today, String defaultGym, String type) throws SQLException {
        conn = sql.dbconnection();
        String classes;
        String classesx = "";
        String message = "Class ID | Class name | \t Time |\t Date |\t Room Nr |\t Instructor |\n";
        Statement stmt = null;
        String query = "select class.classID, class.className, class.time, class.date, class.availableSlots, room.roomID, gym.location, member.fName, member.lName from class natural join instructor natural join room natural join gym NATURAL join classType "+
                "join member on member.memberID=instructor.memberID where class.date = '"+today+"' AND classType.classType like '"+type+"' AND room.roomID in (select room.roomID from gym natural join room where gym.location='"+defaultGym+"') order by class.classID";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String classID = rs.getString("classID");
                String classname = rs.getString("classname");
                String time = rs.getString("time");
                String date = rs.getString("date");
                int roomID = rs.getInt("roomID");
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                classes = (classID + " |\t "+ classname + " |\t " + time + " |\t " + date + " |\t " + roomID + " |\t " + fName + " " + lName + "\n");
                classesx = classesx + classes;
            }
        }
        catch (SQLException e) {
            showMessageDialog(null, "Error while reading classes");
            System.out.println(e.toString());
        }
        finally {
            if (stmt != null) { stmt.close(); }
            conn.close();
        }
        String result = message + classesx;
        return result;
    }
    public static ResultSet getBookedClasses (String memberID) throws SQLException {
        conn = dbconnection();
        java.util.Date realDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(realDate);
        c.add(Calendar.DATE, 0);
        realDate = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //Date in format of SQL database
        String todayy = (sdf.format(realDate));

        String bookedQuery = "select class.classID, class.className, class.date, class.time,  room.roomID from class natural join memberClass natural join room where memberClass.memberID = '" + memberID + "' AND class.date >= '"+todayy+"';";
        ResultSet rs = null;
        try {
            return conn.createStatement().executeQuery(bookedQuery);
        }
        catch (SQLException e) {
            showMessageDialog(null, "Error fetching classes");
            System.out.println(e.toString());
        }
        return rs;
    }
    public static void addnewmember (String sqladd, String name) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate((sqladd));
            conn.commit();
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Success! " + name + " is now a registered member");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!");
        }
        System.out.println("new member done");
    }
    public static void addClass (String sqladd, String newname) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = (sqladd);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Success! " + newname + " is now a new class");
        }
        catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Something went wrong!");
        }
        System.out.println("add new class done");
    }
    public static void createClass (String classsql) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = (classsql);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Success! Class added!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "There is already a class booked for this room on this time and date!");
        }
        System.out.println("new class done");
    }
    public static void addnewinstruct(String addnewinstructsql, String memberIDs) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = (addnewinstructsql);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Success! Member with member ID: " + memberIDs + ", is now a registered instructor");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong");
        }
        System.out.println("new instructor done");
    }
    public static void bookClass (String sqlbookclass) throws SQLException {
        conn = dbconnection();
        Statement stmt;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = (sqlbookclass);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to book a class\n logging you out...");
            e.printStackTrace();
            fitnessAB.login();
        }
        System.out.println("booked to class");
    }
    public static ResultSet confirmClass (String sqlconfirm) throws SQLException {
        conn = dbconnection();
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sqlconfirm);
            return rs;
        }
        catch (SQLException e) {
            showMessageDialog(null,"Error trying to show confirm window");
        }
        conn.close();
        return rs;
    }
    public static ResultSet checkFull (String query) throws SQLException {
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(query);
            return rs;
        }
        catch (SQLException e) {
            showMessageDialog(null,"Error checking status of class");
        }
        finally {
            conn.close();
            rs.close();
        }
     return rs;
    }
    public static String sqlinstructorID(String instructorName) throws SQLException {
        ResultSet rs2 = null;
        String error = "-";
        try {
            conn = dbconnection();
            String sqlReadInstructorID = ("select instructorID from member natural join instructor where fName ='" + instructorName + "';");     // -
            rs2 = conn.createStatement().executeQuery(sqlReadInstructorID);                         // - Dessa tre rader läser in medlemsID
            return rs2.getString("instructorID");
        }
        catch (SQLException e) {
            showMessageDialog(null, "Error getting InstructorID");
        }
        finally {
            rs2.close();
            conn.close();
        }
        return error;
    }
    public static ResultSet getAccountInformation(String memberID) throws SQLException {
        conn = dbconnection();
        ResultSet rs3 = null;
        String error = "Din morsa";
        try {
            String getAccountInfo = "select member.fName, member.lName, member.email, member. phoneNr, gym.location, member.memberID, memberTiers.tierName from member inner join gym on member.defaultGym = gym.gymID inner join memberTiers on member.tierType = memberTiers.tierType where memberID = '" +memberID+ "';";
            rs3 = conn.createStatement().executeQuery(getAccountInfo);
            System.out.println("Fetching member information");
            return rs3;
        }
        catch (SQLException e) {
            e.printStackTrace();
            showMessageDialog(null,"Error fetching account information");
            System.out.println(e.toString());
        }
        rs3.close();
        conn.close();
        return rs3;
    }
    public static ResultSet getAccountInformation2(String memberID) throws SQLException {
        conn = dbconnection();
        ResultSet rs = null;
        String error = "";
        try {
            String getAccountInfo = "select member.email, member. phoneNr, gym.location, memberTiers.tierName from member inner join gym on member.defaultGym = gym.gymID inner join memberTiers on member.tierType = memberTiers.tierType where memberID = '" +memberID+ "';";
            rs = conn.createStatement().executeQuery(getAccountInfo);
            System.out.println("Fetching member information");
            return rs;
        }
        catch (SQLException e) {
            showMessageDialog(null,"Error fetching account information");
            System.out.println(e.toString());
        }
        rs.close();
        conn.close();
        return rs;
    }
    public static void cancelBooking(String query) throws SQLException {
        conn = dbconnection();

        try {
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            conn.commit();
            stmt.close();
            conn.close();
            System.out.println("Removed reservation");
        }
        catch (SQLException var3) {
            JOptionPane.showMessageDialog(null, "Couldn't remove reservation, please contact your gym");
            var3.printStackTrace();
            fitnessAB.login();
        }
    }
    public static ResultSet getInventory (String gym) throws SQLException {
        conn = dbconnection();
        ResultSet rs = null;
        String query = "select equipment, quantity from gymEquipment where gymID = '"+gym+"';";
        try {
            rs = conn.createStatement().executeQuery(query);
            System.out.println("Loading inventory");
        }
        catch (SQLException var3) {
            JOptionPane.showMessageDialog(null, "Could not load inventory for desired gym");
            var3.printStackTrace();
            fitnessAB.login();
        }
        String apa = rs.getString("equipment");
        System.out.println("I SQL " + apa);
        return rs;
    }
    public static void removeCLassSql(String classID, String removeClassQuery) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = (removeClassQuery);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
            JOptionPane.showMessageDialog(null, "Success! " + classID + " is now removed from classes");
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error removing class");
        }
        System.out.println("Class removed");
    }
    public static void ChangeTier(String uname, int newTier) throws SQLException {
        conn = dbconnection();
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = ("update member set tierType = '" + newTier+ "'where email = '"+ uname +"';");
            stmt.executeUpdate(sql);
            conn.commit();
            showMessageDialog(null,"Tier for user: "+uname+", is now updated to: "+newTier+" .");
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String GetNewMemberID () throws SQLException {
        conn = dbconnection();
        String newMemberID = "";
        ResultSet rs = null;
        String query = "select memberID from member order by memberID desc limit 1;";
        rs = conn.createStatement().executeQuery(query);
        int oldestHighestID = rs.getInt(1);
        oldestHighestID = oldestHighestID +1;
        newMemberID = Integer.toString(oldestHighestID);
        conn.close();
        return newMemberID;
    }
}


