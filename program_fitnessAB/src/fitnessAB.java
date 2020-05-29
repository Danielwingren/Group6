import javax.imageio.ImageIO;
import javax.swing.*;
import org.sqlite.SQLiteConfig;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class fitnessAB {
   public static void main (String [] arg) throws SQLException {

      //sql.dbconnection();

      login();
   }
   public static void login() throws SQLException {

      JLabel bild = new JLabel(new ImageIcon(fitnessAB.class.getResource("images/login.png")));

      JTextField userField = new JTextField(14);

      JPasswordField pwField = new JPasswordField(14);

      JPanel myPanel = new JPanel();
      myPanel.setLayout(new GridLayout(2,0));
      //myPanel.add(bild);
      myPanel.add(new JLabel("Email"));
      myPanel.add(userField);
      myPanel.add(Box.createHorizontalStrut(8)); // a spacer
      myPanel.add(new JLabel("Password"));
      myPanel.add(pwField);
         while (true) {
         ImageIcon bild1 = new ImageIcon(fitnessAB.class.getResource("images/login.png"));
         int result = JOptionPane.showConfirmDialog(null, myPanel, "Fitness AB login", JOptionPane.OK_CANCEL_OPTION, 0, bild1);
         if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            System.exit(22);
         }
         String username = userField.getText();
         String password = pwField.getText();
         if (username.isEmpty() || password.isEmpty()) {
            int val = JOptionPane.showConfirmDialog(null, "You have to enter your correct credentials, do you wish to try again?", "Error", JOptionPane.YES_NO_OPTION);
            if (val == JOptionPane.NO_OPTION || val == JOptionPane.CLOSED_OPTION) {
               System.exit(1337);
            }
         } else if (sqlLogin(username, password)) {
            menu(username); //detta programmet initierar huvudmenynd
            break;

         } else {
            break;
         }
      }
   }
   private static void menu(String username) throws SQLException { //forslar vidare beroende medlem eller ej
      ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/logo_greeen.png"));

      String fnamn = sql.getName(username);
      int tier = sql.GetTier(username);
      String memberID = sql.GetMemberID(username);
      String uname = username;
      String defaultGym = sql.getHomeGym(memberID);
      System.out.println("Namn: "+fnamn+"\nTier: "+tier+"\nMemberID: "+memberID);
      System.out.println("Gym: " + defaultGym);
      if (tier == 5) {
         JFrame frame = new JFrame();
         String[] options = new String[2];
         options[0] = "Log in as staff";
         options[1] = "Log in as member";
         int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ", please choose options below:", "Select login view", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
         if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
         }
         if (val == 1) {
            tier = (tier - 1);
         }
      }
      System.out.println(fnamn+" inloggad som "+ tier +", med medlemsnummer "+memberID+" startar huvudmeny");
      if (tier == 5) {
         staffView.mainmenu(memberID, tier, fnamn, username, defaultGym);
      }
      else {
         classbooking.memberscreen(memberID, tier, fnamn, username, defaultGym);
      }
   }
   public static boolean sqlLogin(String uname, String pw) throws SQLException {
         String username = sql.login(uname);
         if (username.isEmpty()) {
            showMessageDialog(null,"Could not find that user, please try again or\nvisit one of our facilitites to register a new membership.\n \nKind Regards\nFitness AB");
         }
         String password = sql.GetPassword(uname);

         int comparePw = pw.compareTo(password);
         if (comparePw == 0) {
            System.out.println("Password match!\nLogging in");
            return true;
         }
         else {
            showMessageDialog(null,"Wrong password","Error",JOptionPane.ERROR_MESSAGE);
            login();
         }
      return false;
   }
}
