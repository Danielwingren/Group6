import javax.swing.*;
import org.sqlite.SQLiteConfig;
import java.sql.*;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class membershipSystem {
    public static void MemberMembershipView (String memberID, int tier, String fnamn) throws SQLException {

        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[4];
        options[0] = "Change Password";
        options[1] = "Change payment method";
        options[2] = "Update contact information";
        options[3] = "Back to membership menu";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(),"Choose what information to update","Membership: ("+fnamn+")",0,JOptionPane.INFORMATION_MESSAGE,icon,options,null);


    }
    public static void UpdateInformation (String memberID, int tier, String uname, String fnamn) throws SQLException {

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
                changePassword(memberID, tier, uname, fnamn);
            case 1 :
                updatePaymentMethod(memberID);
            case 2 :
                updateContactInformation(memberID);
            case 3 :
                MemberMembershipView(memberID, tier, fnamn);

        }
    }
    public static void changePassword (String memberID, int tier, String uname, String fnamn) throws SQLException {
        String checkOld = sql.GetPassword(memberID);
        String newPw= null;
        String checkNewPw = null;
        String originalpw = null;

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
            UpdateInformation(memberID, tier, uname, fnamn);
        else if  (result == JOptionPane.OK_OPTION) {
            checkOld = oldPassword.getText();
            newPw = newPassword.getText();
            checkNewPw = newPasswordControl.getText();
            originalpw = sql.GetPassword(uname);
        }
        if (checkOld.equals(originalpw)) {
            assert newPw != null;
            if (newPw.equals(checkNewPw)) {
                sql.ChangePassword(uname, newPw);
                break;
            }
            else {
                MemberMembershipView(memberID, tier, fnamn);
            }
        }
        else {
            showMessageDialog(null, "New password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);

        }

        }
        if (tier == 5) {
            staffView.mainmenu(memberID, fnamn, uname);
        }


    }
    public static void updatePaymentMethod (String memberID) {
        showMessageDialog(null,"UNDER CONSTRUCTION");
    }
    public static void updateContactInformation(String memberID) {
        showMessageDialog(null,"UNDER CONSTRUCTION");
    }
}
