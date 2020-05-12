import javax.swing.*;
import java.awt.*;
import java.util.*;
import org.sqlite.SQLiteConfig;
import java.sql.*;

public class fitnessAB {

   public static final String DB_URL = "jdbc:sqlite:db_fitnessAB"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
   public static final String DRIVER = "org.sqlite.JDBC";
   static Connection conn = null;

   public static void main (String [] arg) {
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();
         config.enforceForeignKeys(true);
         conn = DriverManager.getConnection(DB_URL,config.toProperties());
      } catch (Exception e) {
         // Om java-progammet inte lyckas koppla upp sig mot databasen så skrivs ett felmeddelande ut)
         System.out.println( e.toString() );
         System.exit(0);
      }
   login();
   menu(); //första programmet gör är att initiera huvudmenyn
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

      int result = JOptionPane.showConfirmDialog(null, myPanel,
              "Fitness AB login", JOptionPane.OK_CANCEL_OPTION);
      String username = userField.getText();
      String password = pwField.getText();
      System.out.println(username+"\n"+password);

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
}

// hej DANIEL
// WHADDAP DANIBOI
