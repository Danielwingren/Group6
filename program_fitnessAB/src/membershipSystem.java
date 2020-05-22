import javax.swing.*;
import org.sqlite.SQLiteConfig;

import java.awt.*;
import java.sql.*;

import static javax.swing.JOptionPane.*;

public class membershipSystem {

    public static void UpdateInformation(String memberID, int tier, String uname, String fnamn) throws SQLException {

        if (tier == 5) {
            String currentMember = showInputDialog("Enter memberID for the person who wish to update:");
        }
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[5];
        options[0] = "Change password";
        options[1] = "Change payment method";
        options[2] = "View contact information";
        options[3] = "Back to main menu";
        options[4] = "Payment history";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Choose what information to update", "Update Member Information", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0:
                changePassword(memberID, tier, uname, fnamn);
                break;
            case 1:
                updatePaymentMethod(memberID);
                break;
            case 2:
                ViewContactInformation(memberID);
                break;
            case 3:
                classbooking.memberscreen(memberID, tier, fnamn, uname);
                break;
            case 4:
                paymentHistory(memberID);

        }
    }

    public static void changePassword(String memberID, int tier, String uname, String fnamn) throws SQLException {
        String checkOld = sql.GetPassword(memberID);
        String newPw = null;
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
        while (true) {
            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Enter information below", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
                UpdateInformation(memberID, tier, uname, fnamn);
            else if (result == JOptionPane.OK_OPTION) {
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
            } else {
                showMessageDialog(null, "Wrong input of old password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (tier == 5) {
            staffView.mainmenu(memberID, tier, fnamn, uname);
        } else {
            UpdateInformation(memberID, tier, uname, fnamn);
        }
    }

    public static void updatePaymentMethod(String memberID) {
        showMessageDialog(null, "UNDER CONSTRUCTION");

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(5, 1));
        String[] cardtype = {"MasterCard", "American Express", "VISA"};
        JTextField cardnumber = new JTextField(20);
        JTextField cvc = new JTextField(20);
        JTextField dateOfExp = new JTextField(20);
        JTextField cardHolderName = new JTextField(20);

        JComboBox cardtypes = new JComboBox(cardtype);
        cardtypes.setEditable(false);
        info.add(new JLabel("Card number: "));
        info.add(cardnumber);
        info.add(new JLabel("CVC: "));
        info.add(cvc);
        info.add(new JLabel("Date of Expire: "));
        info.add(dateOfExp);
        info.add(new JLabel("Card Holder Name: "));
        info.add(cardHolderName);
        info.add(new JLabel("Card Type: "));
        info.add(cardtypes);
        int val = JOptionPane.showOptionDialog(null, info, "Payment Method", YES_NO_OPTION, INFORMATION_MESSAGE, null, null, null);


    }

    public static void ViewContactInformation(String memberID) {

        showMessageDialog(null, "UNDER CONSTRUCTION");

    }

    public static void paymentHistory(String memberID) throws SQLException {
        showMessageDialog(null, "Här ska det stå payment history");
        System.out.println(memberID);
        int paymentH = sql.GetPaymentHistory(memberID);
        showMessageDialog(null, paymentH);
    }
}