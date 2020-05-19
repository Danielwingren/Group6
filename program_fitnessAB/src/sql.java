import java.sql.*;
import static javax.swing.JOptionPane.*;
import org.sqlite.SQLiteConfig;

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
        System.out.println("Vi nådde finally i password efter att ha klippt anslutningen");
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
            System.out.println("Vi nådde finally i password efter att ha klippt anslutningen");
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
            System.out.println("Vi nådde finally i name");
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
            assert false;
            rs2.close();
        }
        return error;
    }

    public static void ChangePassword(String uname, String newPw) throws SQLException {
        conn = dbconnection();
        int rs3 = 0;
        try {
            String sqlNewPassword = ("update member set loginpw = '" + newPw + "' where email = '" + uname + "';");
            System.out.println("önskad lösenord: " + newPw + "\ntill användare: " + uname);
            rs3 = conn.createStatement().executeUpdate(sqlNewPassword);
        } catch (SQLException e) {
        } finally {

            if (rs3 != 0) {
                rs3.close();
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
    }


    public static void createRoom () throws SQLException {
        try {
            conn = dbconnection();
            Statement st = conn.createStatement();
            st.executeUpdate("insert into room (roomID, gymID) VALUES ('1','1')");
        }
        catch (SQLException e){
            System.out.println(e.toString());
        }

    }
}



