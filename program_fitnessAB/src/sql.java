import java.sql.*;
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
            System.out.println(e.toString());
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
        }
        finally {
        rs.close();
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

        } finally {
            rs.close();
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
        } catch (SQLException e) {
            showMessageDialog(null, "Could not load name of user");
        } finally {

            assert rs != null;
            rs.close();
            conn.close();

        }
        return error;
    }

    public static int GetTier(String username) throws SQLException {
        conn = dbconnection();
        int error = 0;
        ResultSet rs1 = null;
        try {
            String sqlReadTier = ("select tierType from member where email = '" + username + "';");    // -
            rs1 = conn.createStatement().executeQuery(sqlReadTier);                         // -
            String tierType = rs1.getString("tiertype");                                  // - Dessa fyra rader läser av ifall det är en anställd eller ej (läser in tiertype)
            int tier = Integer.parseInt(tierType);
            return tier;
        } catch (SQLException e) {
            showMessageDialog(null, "Could not load tier");
        }
        finally {
            rs1.close();
            conn.close();
        }
        return error;
    }

    public static int getHomeGym(String username) throws SQLException {
        ResultSet rs2 = null;
        int error = 0;
        try {
            conn = dbconnection();

            String sqlGymNo = ("select defaultGym from member where email ='" + username + "';");     // -
            rs2 = conn.createStatement().executeQuery(sqlGymNo);                         // - Dessa tre rader läser in hemmagym
            return rs2.getInt("defaultGym");
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting location name");
        } finally {
            rs2.close();
            conn.close();
        }
        return error;
    }
/*
    public static int getHomeGymName(int gymNO) throws SQLException {
        conn = dbconnection();
        String error = "";
        ResultSet rs1 = null;
        try {
            String sqlReadGymName = ("select location from gym where gymID = '" + gymNO + "';");    // -
            rs1 = conn.createStatement().executeQuery(sqlReadGymName);                         // -
            String location = rs1.getString("location");                                  // - Dessa fyra rader läser av ifall det är en anställd eller ej (läser in tiertype)
            return location;
        } catch (SQLException e) {
            showMessageDialog(null, "Could not load location");
        }
        finally {
            rs1.close();
            conn.close();
        }
        return error;
    } */


    public static String GetMemberID(String username) throws SQLException {
        ResultSet rs2 = null;
        String error = "-";
        try {
            conn = dbconnection();

            String sqlReadMemberID = ("select memberID from member where email ='" + username + "';");     // -
            rs2 = conn.createStatement().executeQuery(sqlReadMemberID);                         // - Dessa tre rader läser in medlemsID
            return rs2.getString("memberID");
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting memberID");
        } finally {
            rs2.close();
            conn.close();
        }
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
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting date");
        } finally {
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
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting amount");
        } finally {
            rs.close();
            conn.close();
        }
        return error;
    }

    // Alternativ lösning till payment history som inte fungerar
    /* public static ResultSet GetPaymentHistory(String memberID) throws SQLException {
        ResultSet rs = null;
        try {
            conn = dbconnection();
            Statement statement = null;
            statement = conn.createStatement();
            String query = ("select date, amount from payments where memberID ='" + memberID + "';");
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting payment history");
            System.out.println(e);
        } finally {
            assert rs != null;
            rs.close();
            conn.close();
        }
        return rs;
    } */

    public static ResultSet GetClassName() throws SQLException {
        ResultSet rs = null;
        //String error = "-";
        try {
            conn = dbconnection();
            String query = ("select classname from class;");
            rs = conn.createStatement().executeQuery(query);
            return rs;
        } catch (SQLException e) {
            showMessageDialog(null, "Error getting classname");
            System.out.println(e);
        } finally {
            rs.close();
            conn.close();
        }
        return rs;
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
        rs.close();
        stmt.close();
        conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fitnessAB.login();
    }
    public static ResultSet ViewAllClasses () throws SQLException {
        conn = dbconnection();
        ResultSet rs = null;
        String query = "";
        try {
            query = "select class.className, class.time, class.date, class.roomID , member.fName, member.lName from member join instructor on member.memberID = instructor.memberID natural join class";
            rs = conn.createStatement().executeQuery(query);
            return rs;
        }
        catch (SQLException e) {
            showMessageDialog(null,"DET ÄR FEL");
            System.out.println(e.toString());
        }
        finally {
            assert rs != null;
            rs.close();
            conn.close();
        }
        return rs;
    }
    public static ResultSet getBookedClasses (String memberID) throws SQLException {
        conn = dbconnection();
        ResultSet rs = null;
        String bookedQuery = "";
        try {
            bookedQuery = "select class.className, class.time, class.date, instructor.fName, " +
                    "room.roomID from class natural join memberClass natural join instructor natural join room " +
                    "where memberClass.memberID = '"+memberID+"';";
            rs = conn.createStatement().executeQuery(bookedQuery);
        } catch (SQLException e) {
            showMessageDialog(null, "Fel din idjut");
            System.out.println(e.toString());
        }
        finally {
            conn.close();
            rs.close();
        }
        return rs;
    }
    public static void addnewmember (String sqladd, String name) throws SQLException {
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
            JOptionPane.showMessageDialog(null, "Success! " + name + " is now a registered member");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!");
            staffView.addnewmember();
        }
        System.out.println("new member done");
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
            JOptionPane.showMessageDialog(null, "Sucess! Class added!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong, please try again!");
            staffView.createclass();
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
            JOptionPane.showMessageDialog(null, "Success! " + memberIDs + " is now a registered instructor");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong");
            staffView.addnewinstruct();
        }
        System.out.println("new instructor done");
    }
}





