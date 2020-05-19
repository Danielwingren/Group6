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
            showMessageDialog(null, "Catch on reading old password");
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
    }
    public static ResultSet ViewAllClasses () throws SQLException {
        conn = dbconnection();
        ResultSet rs = null;
        String query = "";
        try {
            query = "select className, time, date, room.roomID, instructor.fName FROM class natural join instructor natural join room;";
            rs = conn.createStatement().executeQuery(query);
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
}



