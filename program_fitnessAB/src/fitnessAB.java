import javax.swing.*;

import org.sqlite.SQLiteConfig;
import java.sql.*;

public class fitnessAB {

   public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
   public static final String DRIVER = "org.sqlite.JDBC";
   static Connection conn = null;

   public static void main (String [] arg) {
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
   login(); //Denna har jag alltså ändrat om lite så att den nu frågar efter både mail och lösenord i första fönsret

   }
   private static void login() {

      JTextField userField = new JTextField(14);
      JPasswordField pwField = new JPasswordField(14);

      JPanel myPanel = new JPanel();
      myPanel.add(new JLabel("Email"));
      myPanel.add(userField);
      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
      myPanel.add(new JLabel("Password"));
      myPanel.add(pwField);
      while (true) {
         int result = JOptionPane.showConfirmDialog(null, myPanel,
                 "Fitness AB login", JOptionPane.OK_CANCEL_OPTION);
         if (result == JOptionPane.CANCEL_OPTION){
            System.exit(2);
         }
         System.out.println(result);
         String username = userField.getText();
         String password = pwField.getText();
         System.out.println(username + "\n" + password);
         if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showConfirmDialog(null, "You have to enter your correct credentials, do you wish to try again?", "Error", JOptionPane.YES_NO_OPTION);
         } else if (sqlLogin(username, password)) {
            menu(); //detta programmet initierar huvudmenyn
         } else {
            System.exit(3);
         }
      }
   }
     /*String user = JOptionPane.showInputDialog(null, "Username?"); JAG ÄNDRADE SÅ ATT DENNA FRÅGA STÄLLS SÅ ATT MAN FÅR BÅDE ANVÄNDARE OCH MÖJLIGHETEN ATT KOLLA LÖSENORD I EN RUTA //Dannyboii
     System.out.println(user);*/
   private static void menu() { //MENY

    Object[] options1 = {"Cancel", "Staff", "Member"};

     int i = JOptionPane.showOptionDialog(null,
                 "Welcome to FitnessAB, how would you like to log in?",
                 "FitnessAB", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);

     System.out.println(i);

      switch(i) { //switch satsen talar om hur programmet skall gå vidare beroende på vad användaren väljer
         case 0:
            JOptionPane.showMessageDialog(null, "Thank you see you next time");
               System.exit(0);
            break;
         case 1:
            JOptionPane.showMessageDialog(null, "Welcome Staff Member");
               break;
         case 2:
            JOptionPane.showMessageDialog(null, "Welcome Member");
               break;
         default:
            menu();


      }
   }
      public static void Adminlogin () { //metod Adminlogin

         String Uname = JOptionPane.showInputDialog(null, "Username?"); //Extremt simplifierad username checker för admin
         String Pass = JOptionPane.showInputDialog(null, "Password?");
         String unpass = Uname + Pass;


         switch(unpass) { //Kollar om admin har rätt login
            case "admin123":
               JOptionPane.showConfirmDialog(null, "Welcome admin, what would you like to do?", "Admin Page",
                                                  JOptionPane.YES_NO_OPTION);
                  break;

         default:
            JOptionPane.showMessageDialog(null, "Please try again");
            Adminlogin();
      }

   }
   public static boolean sqlLogin (String uname, String pw) {

      String a = JOptionPane.showInputDialog("gå till meny --> m\navsluta --> q");
      switch (a) {
         case "m":
            return true;
         case "q":
            return false;
      }
      return false;
   }
}

// hej DANIEL
// WHADDAP DANIBOI
