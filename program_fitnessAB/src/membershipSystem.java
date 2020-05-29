import javax.swing.*;
import org.sqlite.SQLiteConfig;

import java.awt.*;
import java.sql.*;

import static javax.swing.JOptionPane.*;

public class membershipSystem {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void UpdateInformation(String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {


        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[6];
        options[0] = "Change password";
        options[1] = "Change payment method";
        options[2] = "View contact information";
        options[3] = "Update contact information";
        options[5] = "Back to main menu";
        options[4] = "Payment history";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Choose what information to update", "Update Member Information", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0:
                changePassword(memberID, tier, uname, fnamn, defaultGym);
                break;
            case 1:
                updatePaymentMethod(memberID);
                break;
            case 2:
                ViewContactInformation(memberID, tier, uname, fnamn, defaultGym);
                break;
            case 3:
                UpdateContactInformation(memberID, tier, uname, fnamn, defaultGym);
            case 5:
                classbooking.memberscreen(memberID, tier, fnamn, uname, defaultGym);
                break;
            case 4:
                paymentHistory(memberID, tier, fnamn, uname, defaultGym);
                break;

        }
    }

    public static void changePassword(String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {
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
                UpdateInformation(memberID, tier, uname, fnamn, defaultGym);
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
            staffView.mainmenu(memberID, tier, fnamn, uname, defaultGym);
        } else {
            UpdateInformation(memberID, tier, uname, fnamn, defaultGym);
        }
    }

    public static void updatePaymentMethod(String memberID) {

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

    public static void ViewContactInformation(String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {

        ResultSet rs3 = sql.getAccountInformation(memberID);
        System.out.println(rs3);
        int i = 1;
        String fName = rs3.getString(1);
        String lName = rs3.getString(2);
        String email = rs3.getString(3);
        String phoneNr = rs3.getString(4);
        String location = rs3.getString(5);
        String memberIDx = rs3.getString(6);
        String tierName = rs3.getString(7);
        showMessageDialog(null, "First name: " + fName+ "\nLast name: " + lName + "\nE-mail: " + email + "\nPhone number: " + phoneNr + "\nHome gym: " + location + "\nMember ID: " + memberIDx + "\nTier: " + tierName + "");
        membershipSystem.UpdateInformation(memberID, tier, fnamn, uname, defaultGym);
    }

    public static void UpdateContactInformation(String memberID, int tier, String uname, String fnamn, String defaultGym)throws SQLException {
        // email, phone nr, home gym, tier
        ResultSet rs = sql.getAccountInformation2(memberID);
        System.out.println(rs);
        Statement stmt = conn.createStatement();
        int i = 1;
        String email = rs.getString(1);
        String phoneNr = rs.getString(2);
        String location = rs.getString(3);
        String tierName = rs.getString(4);
        String newemail = JOptionPane.showInputDialog(null, "E-mail: ", email);
        String newphonenr = JOptionPane.showInputDialog(null, "Phone number: ", phoneNr);
        String newhomegym = JOptionPane.showInputDialog(null, "Home gym: ", location);
        String newtier = JOptionPane.showInputDialog(null, "Tier: ", tierName);
        String updatesql = ("update member set email = '" + newemail + "' where memberID = '" + memberID + "';");
        stmt.executeUpdate(updatesql);
        String updatesql2 = ("update member set phoneNr = '" + newphonenr + "' where memberID = '" + memberID + "';");
        stmt.executeUpdate(updatesql2);
        String updatesql3 = ("update member set location = '" + newhomegym + "' where memberID = '" + memberID + "';");
        stmt.executeUpdate(updatesql3);
        String updatesql4 = ("update member set tierName = '" + newtier + "' where memberID = '" + memberID + "';");
        stmt.executeUpdate(updatesql4);
        membershipSystem.UpdateInformation(memberID, tier, fnamn, uname, defaultGym);
    }

    public static void paymentHistory(String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        // Alternativ lösning till payment history som inte fungerar
      /*ResultSet rs = sql.GetPaymentHistory(memberID);
        while(rs.next()) {
            int date = rs.getInt("date");
            int amount = rs.getInt("amount");

            showMessageDialog(null, date + amount);
        } */

      // Fixa så det visar endast månad och år som i interface samt slå ihop amount per månad?
      String date = sql.GetPaymentDate(memberID);
      String amount = sql.GetPaymentAmount(memberID);
      showMessageDialog (null, "Payment History:\n" + "Date:" + "         " + "Amount:\n" + date + "           " + amount);
      membershipSystem.UpdateInformation(memberID, tier, fnamn, uname, defaultGym);
    }
}