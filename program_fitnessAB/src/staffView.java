import org.sqlite.SQLiteConfig;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showInputDialog;

public class staffView {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void mainmenu (String memberID, int tier, String fnamn, String uname) throws SQLException {
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/logo_greeen.png"));
        tier = sql.GetTier(uname);
        while (true) {

            JFrame frame = new JFrame();
            String[] options = new String[3];
            options[0] = "Add new member";
            options[1] = "Update member information";
            options[2] = "Change your password";
            int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ", please choose operation below:", "Main Menu", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
            if (val == JOptionPane.CLOSED_OPTION) {
                System.exit(11);
            }
            switch (val) {
                case 0 :
                    addnewmember();
                    break;
                case 1 :
                    staffView.UpdateInformation(memberID, tier, uname, fnamn);
                    break;
                case 2 :
                    membershipSystem.changePassword(memberID, tier, uname, fnamn);
                    break;
            }
            break;
        }
    }

    public static void addnewmember() throws SQLException {
        System.out.println("eh. BOOM");

        JTextField fName = new JTextField(14);
        JTextField lName = new JTextField(14);
        JTextField cardNo = new JTextField(14);
        JTextField tierType = new JTextField(14);
        JTextField phone = new JTextField(14);
        JTextField email = new JTextField(14);
        JPasswordField loginpw = new JPasswordField(14);
        JTextField creditCard = new JTextField(14);
        JTextField pNr = new JTextField(14);
        JTextField defaultGym = new JTextField(14);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(5,1));
        //myPanel.add(bild);
        myPanel.add(new JLabel("First Name"));
        myPanel.add(fName);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Last Name"));
        myPanel.add(lName);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Card no"));
        myPanel.add(cardNo);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Tier"));
        myPanel.add(tierType);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Phone"));
        myPanel.add(phone);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Email"));
        myPanel.add(email);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Password"));
        myPanel.add(loginpw);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("creditCard"));
        myPanel.add(creditCard);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Personnummer"));
        myPanel.add(pNr);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer
        myPanel.add(new JLabel("Home Gym"));
        myPanel.add(defaultGym);
        myPanel.add(Box.createHorizontalStrut(8)); // a spacer

        String fNames = fName.getText();
        String lNames = lName.getText();
        String cardNumbers = cardNo.getText();
        String tiers = tierType.getText();
        String phones = phone.getText();
        String emails = email.getText();
        String passwords = loginpw.getText();
        String creditCards = creditCard.getText();
        String personNummers = pNr.getText();
        String defaultGyms = defaultGym.getText();

        System.out.println(phones + tiers + creditCards);

        String addnewsql = "INSERT INTO member" +
                "(\"memberID, \"fName\", \"lName\", \"card#\", \"tierType\", \"phone#\", \"email\", \"loginpw\", \"creditCardNumber\", \"pNr\", \"defaultGym\")" +
                "VALUES ('"+fNames+"', '"+lNames+"', '"+cardNumbers+"', '"+tiers+"', '"+phones+"', '"+tiers+"', '"+emails+"', '"+passwords+"', '"+creditCards+"', '"+personNummers+"', '"+defaultGyms+"');";

        ImageIcon bild1 = new ImageIcon (fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, myPanel, "Fitness AB login", JOptionPane.OK_CANCEL_OPTION,0,bild1);
        System.out.println(addnewsql);
        //sql.addnewmember(addnewsql);
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
                membershipSystem.changePassword(memberID, tier, uname, fnamn);
            case 1 :
                membershipSystem.updatePaymentMethod(memberID);
            case 2 :
                membershipSystem.updateContactInformation(memberID);
            case 3 :
                staffView.mainmenu(memberID, tier, uname, fnamn);

        }
    }
}

//hej daniel