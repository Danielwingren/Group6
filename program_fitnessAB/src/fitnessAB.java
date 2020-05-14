import javax.imageio.ImageIO;
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
   login();
   }
   private static void login() throws SQLException {
      JLabel bild = new JLabel(new ImageIcon(fitnessAB.class.getResource("images\\login.png")));

      JTextField userField = new JTextField(14);
      JPasswordField pwField = new JPasswordField(14);

      JPanel myPanel = new JPanel();
      //myPanel.add(bild);
      myPanel.add(new JLabel("Email"));
      myPanel.add(userField);
      myPanel.add(Box.createHorizontalStrut(8)); // a spacer
      myPanel.add(new JLabel("Password"));
      myPanel.add(pwField);

      while (true) {
         ImageIcon bild1 = new ImageIcon (fitnessAB.class.getResource("images\\login.png"));
         int result = JOptionPane.showConfirmDialog(null, myPanel,
                 "Fitness AB login", JOptionPane.OK_CANCEL_OPTION,0, (Icon) bild1);
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
            break;
         } else {
            System.exit(333);
         }
      }
   }
   private static void menu(String username) throws SQLException { //forslar vidare beroende medlem eller ej
      ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/logo_greeen.png"));


      String sqlname= ("select fName, lName from member where email = '" + username + "';"); //-
      ResultSet rs = conn.createStatement().executeQuery(sqlname);                           //- - Dessa tre rader tar fram namnet på den inloggade medlemmen
      String fnamn = rs.getString("fName");                                      //-
      String sqlReadTier= ("select tierType from member where email = '" + username + "';");    // -
      ResultSet rs1 = conn.createStatement().executeQuery(sqlReadTier);                         // -
      String tierType = rs1.getString("tiertype");                                  // - Dessa fyra rader läser av ifall det är en anställd eller ej
      int tier = Integer.parseInt(tierType);                                                    // -
      String sqlReadMemberID= ("select memberID from member where email ='" + username + "';");     // -
      ResultSet rs2 = conn.createStatement().executeQuery(sqlReadMemberID);                         // - Dessa tre rader läser in medlemsID
      String memberID = rs2.getString("memberID");                                      // -

      System.out.println(fnamn+" inloggad som "+ tierType +", med medlemsnummer "+memberID+" startar huvudmeny");
      while(true) {
         JFrame frame = new JFrame();
         String[] options = new String[3];
         options[0] = "Start classbooking program";
         options[1] = "Start member management program";
         options[2] = "Change your password";
         int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ", please choose operation below:", "Main Menu", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
         if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
         }
         switch (val) {
            case 0:
               if (tier == 5) {
                  classbooking.employeeScreen(fnamn);
               } else {
                  classbooking.memberscreen(fnamn);
               }
               break;
            case 1:
               if (tier == 5) {
                  membershipSystem.EmployeeMembershipView(memberID, tier);
               } else {
                  membershipSystem.MemberMembershipView(memberID, tier);
               }
               break;
            case 2:
               membershipSystem.changePassword(memberID,tier);
         }
      }
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
