import javax.swing.*;
import org.sqlite.SQLiteConfig;
import java.sql.*;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class fitnessAB {

   public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
   public static final String DRIVER = "org.sqlite.JDBC";
   static Connection conn = null;

   public static void main (String [] arg) throws SQLException {
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();
         config.enforceForeignKeys(true);
         conn = DriverManager.getConnection(DB_URL,config.toProperties());
      } catch (Exception e) {
         // Om den inte lyckas skapa en anslutning till databasen så bör vi få ett felmeddelande
         System.out.println( e.toString() );
         System.exit(0);
      }
      //membersystem.testmember();
      //staffsystem.teststaff();
   login();
   }
   private static void login() throws SQLException {

      JTextField userField = new JTextField(14);
      JPasswordField pwField = new JPasswordField(14);

      JPanel myPanel = new JPanel();
      myPanel.add(new JLabel("Email"));
      myPanel.add(userField);
      myPanel.add(Box.createHorizontalStrut(8)); // a spacer
      myPanel.add(new JLabel("Password"));
      myPanel.add(pwField);
      while (true) {
         int result = JOptionPane.showConfirmDialog(null, myPanel,
                 "Fitness AB login", JOptionPane.OK_CANCEL_OPTION);
         if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION){
            System.exit(22);
         }
         //System.out.println(result);
         String username = userField.getText();
         String password = pwField.getText();
         //System.out.println(username + "\n" + password);
         if (username.isEmpty() || password.isEmpty()) {
            int val = JOptionPane.showConfirmDialog(null, "You have to enter your correct credentials, do you wish to try again?", "Error", JOptionPane.YES_NO_OPTION);
            if (val == JOptionPane.NO_OPTION || val == JOptionPane.CLOSED_OPTION) {
               System.exit(1337);
            }
         } else if (sqlLogin(username, password)) {
            menu(username); //detta programmet initierar huvudmenyn
         } else {
            System.exit(333);
         }
      }
   }

   private static void menu(String username) throws SQLException { //forslar vidare beroende medlem eller ej

      String sqlname= ("select fName, lName from member where email = '" + username + "';");
      ResultSet rs = conn.createStatement().executeQuery(sqlname);
      String fnamn = rs.getString("fName");
      showMessageDialog(null,"Välkommen "+fnamn+" forslar nu vidare dig till huvudmenyn");

   }
   public static boolean sqlLogin (String uname, String pw) throws SQLException {
      try {
         Statement st = conn.createStatement();
         String sql = ("select email from member where email = '" + uname.toLowerCase() + "';");
         ResultSet rs = st.executeQuery(sql);
         String user = rs.getString("email");
         int compare = uname.compareTo(user);
         //System.out.println("Compare uname --> user = "+compare);
         if (compare == 0) {
            System.out.println("userID matches,\nchecking password:");
            String sqlpw = ("select loginpw from member where email = '" + uname + "';");
            rs = conn.createStatement().executeQuery(sqlpw);
            String password = rs.getString("loginpw");
            //System.out.println(password + " --> " + pw);
            int comparePw = pw.compareTo(password);
            //System.out.println(comparePw);
            if (comparePw == 0) {
               System.out.println("Password match!\nLogging in");
               return true;
            } else  {
               System.out.println("Passowrd incorrect.");
               int choice = showConfirmDialog(null, "The password is not correct.\nDo you want to try again?", "Error", JOptionPane.YES_NO_OPTION);
               if (choice == JOptionPane.YES_OPTION) {
                  login();
               } else {
                  System.exit(15);
               }
            }
         }
         else {
         JOptionPane.showMessageDialog(null, "VARFÖR HAMNAR JAG HÄR");
         }
      }
      catch (Exception e) {
         System.out.println( e.toString() );
         int response = showConfirmDialog(null, "Cannot find that username.\nDo you want to try again?", "Error", JOptionPane.YES_NO_OPTION);
         if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
            System.exit(2);
         }
         else {
            login();
         }
      }
      return false;
   }
}
