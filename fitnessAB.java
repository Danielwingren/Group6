import javax.swing.*;
import java.awt.*;
import java.util.*;

public class fitnessAB {
   public static void main (String [] arg) {
   
   menu(); //första programmet gör är att initiera huvudmenyn
   }
   
   private static void menu () { //MENY
   
    Object[] options1 = {"Cancel", "Admin", "Staff", "Member"};

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
            Adminlogin();
               break;
         case 2:
            JOptionPane.showMessageDialog(null, "Welcome Staff Member");
               break;
         case 3:
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