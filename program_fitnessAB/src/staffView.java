import org.sqlite.SQLiteConfig;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showInputDialog;

public class staffView {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void mainmenu (String memberID, String fnamn) {
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/logo_greeen.png"));
        while (true) {

            JFrame frame = new JFrame();
            String[] options = new String[3];
            options[0] = "Start classbooking program";
            options[1] = "Start member management program";
            options[2] = "Change your password";
            int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ", please choose operation below:", "Main Menu", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
            if (val == JOptionPane.CLOSED_OPTION) {
                System.exit(11);
            }
        }
    }

    public static void EmployeeMembershipView (String memberID, int tier) throws SQLException {

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
        JFrame frame = new JFrame();
        String[] options = new String[3];
        options[0] = "Add new member";
        options[1] = "Update Member Information";
        options[2] = "och så vidare...";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Choose action below", "Membership (Admin)", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0 :
                addnewmember();
                break;
            case 1 :
                UpdateInformation(memberID, tier);
                break;
            case 2 :
                break;
        }

    }
    public static void addnewmember() {
        try {
            Class.forName(DRIVER);
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(DB_URL, config.toProperties());
        } catch (Exception e) {
            // Om den inte lyckas skapa en anslutning till databasen så bör vi få ett felmeddelande
            System.out.println(e.toString());
            System.exit(0);

            JLabel bild = new JLabel(new ImageIcon(fitnessAB.class.getResource("images/settings.png")));

            JTextField userField = new JTextField(14);
            JPasswordField pwField = new JPasswordField(14);

            JPanel myPanel = new JPanel();
            //myPanel.add(bild);
            myPanel.add(new JLabel("First Name"));
            myPanel.add(userField);
            myPanel.add(Box.createHorizontalStrut(8)); // a spacer
            myPanel.add(new JLabel("Last Name"));
            myPanel.add(pwField);
        }
    }
    public static void UpdateInformation (String memberID, int tier) throws SQLException {

        String currentMember = showInputDialog("Enter memberID for the person who wish to update:");
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[4];
        options[0] = "Change Password";
        options[1] = "Change payment method";
        options[2] = "Update contact information";
        options[3] = "Back to membership menu";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(),"Choose what information to update","Update Member Information",0,JOptionPane.INFORMATION_MESSAGE,icon,options,null);

        switch (val) {
            case 0 :
                membershipSystem.changePassword(memberID, tier);
            case 1 :
                membershipSystem.updatePaymentMethod(memberID);
            case 2 :
                membershipSystem.updateContactInformation(memberID);
            case 3 :
                EmployeeMembershipView(memberID, tier);

        }
    }
}
