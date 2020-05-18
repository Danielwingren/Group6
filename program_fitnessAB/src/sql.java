import java.sql.*;
import static javax.swing.JOptionPane.*;
import org.sqlite.SQLiteConfig;

public class sql {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static Connection dbconnection() {
        try {
            Class.forName(DRIVER);
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(DB_URL, config.toProperties());
        }
        catch (Exception e) {
            // Om den inte lyckas skapa en anslutning till databasen så bör vi få ett felmeddelande
            System.out.println(e.toString());
            System.exit(0);
        }
        return conn;
    }
    public static String login (String uname) throws SQLException {
        conn = dbconnection();
        String error = "-";
        try {
            Statement st = conn.createStatement();
            String sql = ("select email from member where email = '" + uname.toLowerCase() + "';");
            ResultSet rs = st.executeQuery(sql);
            String user = rs.getString("email");
            return user;
        }
        catch (SQLException e) {
            showMessageDialog(null,"Could not find that user, please try again or\nvisit one of our facilitites to register a new membership.\n \nKind Regards\nFitness AB");
        }
        return error;
    }

    public static String GetPassword(String uname) throws SQLException {
        conn = dbconnection();
        String error = "-";
        try {
            String sqlpw = ("select loginpw from member where email = '" + uname + "';");
            ResultSet rs = conn.createStatement().executeQuery(sqlpw);
            String password = rs.getString("loginpw");
            return password;
        }
        catch (SQLException e) {
            fitnessAB.login();
        }
        return error;
    }
    public static String getName(String username) throws SQLException {
        conn = dbconnection();
        String sqlname= ("select fName, lName from member where email = '" + username + "';"); //-
        ResultSet rs = conn.createStatement().executeQuery(sqlname);                           //- - Dessa tre rader tar fram namnet på den inloggade medlemmen
        String fnamn = rs.getString("fName");                                      //-
        return fnamn;
    }
    public static int tier (String username) throws SQLException {
        conn = dbconnection();
        String sqlReadTier= ("select tierType from member where email = '" + username + "';");    // -
        ResultSet rs1 = conn.createStatement().executeQuery(sqlReadTier);                         // -
        String tierType = rs1.getString("tiertype");                                  // - Dessa fyra rader läser av ifall det är en anställd eller ej (läser in tiertype)
        int tier = Integer.parseInt(tierType);
        return tier;                                                                                // -
    }
    public static String GetMemberID (String username) throws SQLException {
        conn = dbconnection();
        String sqlReadMemberID= ("select memberID from member where email ='" + username + "';");     // -
        ResultSet rs2 = conn.createStatement().executeQuery(sqlReadMemberID);                         // - Dessa tre rader läser in medlemsID
        String memberID = rs2.getString("memberID");                                      // -
        return memberID;
    }


}



