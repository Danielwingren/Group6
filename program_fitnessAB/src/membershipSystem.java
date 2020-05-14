import javax.swing.*;
import org.sqlite.SQLiteConfig;
import java.sql.*;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class membershipSystem {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

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

                break;
            case 1 :
                UpdateInformation(memberID, tier);
                break;
            case 2 :
                break;
        }

    }
    public static void MemberMembershipView (String memberID, int tier) throws SQLException {
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
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[4];
        options[0] = "Change Password";
        options[1] = "Change payment method";
        options[2] = "Update contact information";
        options[3] = "Back to membership menu";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(),"Choose what information to update","Membership (Member)",0,JOptionPane.INFORMATION_MESSAGE,icon,options,null);

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
                changePassword(memberID, tier);
            case 1 :
                updatePaymentMethod(memberID);
            case 2 :
                updateContactInformation(memberID);
            case 3 :
                if (tier == 5) {
                    EmployeeMembershipView(memberID, tier);
                }
                else {
                    MemberMembershipView(memberID, tier);
                }

        }
    }
    public static void changePassword (String memberID, int tier) throws SQLException {
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
        String checkOld = null;
        String newPw= null;
        String checkNewPw = null;

        String sqlCurrentPassword = ("select loginpw from member where memberID = '" + memberID + "';");
        ResultSet rs = conn.createStatement().executeQuery(sqlCurrentPassword);
        String CurrentPassword = rs.getString("loginpw");

        JPasswordField oldPassword = new JPasswordField(10);
        JPasswordField newPassword = new JPasswordField(10);
        JPasswordField newPasswordControl = new JPasswordField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Enter current password:"));
        myPanel.add(oldPassword);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("New password:"));
        myPanel.add(newPassword);
        myPanel.add(new JLabel("Confirm new password"));
        myPanel.add(newPasswordControl);
        while(true) {
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Enter information below", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
            UpdateInformation(memberID, tier);
        else if  (result == JOptionPane.OK_OPTION) {
            checkOld = oldPassword.getText();
            newPw = newPassword.getText();
            checkNewPw = newPasswordControl.getText();
            System.out.println("old: "+checkOld+"\nnew1: "+newPw+"\nnew2: "+checkNewPw);
        }

            if (checkOld.equals(CurrentPassword)) {
                if (newPw.equals(checkNewPw)) {
                    String sqlNewPassword = ("UPDATE member set loginpw = '" + newPw + "' where memberID ='"+memberID+"';");
                    conn.createStatement().executeQuery(sqlNewPassword);
                    String NewPassword = rs.getString("loginpw");
                    showMessageDialog(null, "INSERT-sqlsats new password\nYour password has been changed.");
                    if (tier == 5) {
                        EmployeeMembershipView(memberID, tier);
                        break;
                    }
                    else {
                        MemberMembershipView(memberID, tier);
                    }
                } else {
                    showMessageDialog(null, "New password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);

                }
            } else {
                showMessageDialog(null, "Wrong current password", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }

    }
    public static void updatePaymentMethod (String memberID) {
        showMessageDialog(null,"UNDER CONSTRUCTION");
    }
    public static void updateContactInformation(String memberID) {
        showMessageDialog(null,"UNDER CONSTRUCTION");
    }
}
