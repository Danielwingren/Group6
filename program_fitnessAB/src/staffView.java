import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class staffView {
    public static void mainmenu (String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/logo_greeen.png"));
        tier = sql.GetTier(uname);
        while (true) {

            JFrame frame = new JFrame();
            String[] options = new String[6];
            options[0] = "Add new member";
            options[1] = "Update member information";
            options[2] = "Add new certificate";
            options[4] = "Logout";
            options[3] = "Create a class";
            options[5] = "Add new Instructor";
            int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ", please choose operation below:", "Main Menu", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
            if (val == JOptionPane.CLOSED_OPTION) {
                System.exit(11);
            }
            switch (val) {
                case 0 :
                    addnewmember();
                    break;
                case 1 :
                    staffView.UpdateInformation(memberID, tier, uname, fnamn, defaultGym);
                    break;
                case 2 :
                    addnewcertificate();
                    break;
                case 4 :
                    fitnessAB.login();
                case 3 :
                    staffView.createclass();
                case 5 :
                    addnewinstruct();
            }
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
        JTextField loginpw = new JTextField(14);
        JTextField creditCard = new JTextField(14);
        JTextField pNr = new JTextField(14);
        JTextField defaultGym = new JTextField(14);
        JTextField memberID = new JTextField(14);
        JPanel newmemberPanel = new JPanel();
        newmemberPanel.setLayout(new GridLayout(12,1));
        //myPanel.add(bild);


        newmemberPanel.add(new JLabel("First Name"));
        newmemberPanel.add(fName);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Last Name"));
        newmemberPanel.add(lName);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Gym card no"));
        newmemberPanel.add(cardNo);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Tier"));
        newmemberPanel.add(tierType);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Phone"));
        newmemberPanel.add(phone);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Email"));
        newmemberPanel.add(email);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Password"));
        newmemberPanel.add(loginpw);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("creditCard"));
        newmemberPanel.add(creditCard);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Personnummer"));
        newmemberPanel.add(pNr);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("Home Gym"));
        newmemberPanel.add(defaultGym);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newmemberPanel.add(new JLabel("MemberID"));
        newmemberPanel.add(memberID);
        newmemberPanel.add(Box.createHorizontalStrut(8)); // a spacer


        ImageIcon bild1 = new ImageIcon (fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newmemberPanel, "New member", JOptionPane.OK_CANCEL_OPTION,0,bild1);
        if (result == JOptionPane.CANCEL_OPTION) {
            fitnessAB.login();
        }
        else if (result == JOptionPane.CLOSED_OPTION) {
            System.exit(22);
        }

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
        String memberIDs = memberID.getText();
        String addnewsql = "INSERT INTO member" +
                "(\"memberID\", \"fName\", \"lName\", \"card#\", \"tierType\", \"phone#\", \"email\", \"loginpw\", \"creditCardNumber\", \"pNr\", \"defaultGym\")" +
                "VALUES ('"+memberIDs+"','"+fNames+"', '"+lNames+"', '"+cardNumbers+"', '"+tiers+"', '"+phones+"', '"+emails+"', '"+passwords+"', '"+creditCards+"', '"+personNummers+"', '"+defaultGyms+"');";
        System.out.println(addnewsql);
        String name = fNames + " " + lNames;
        sql.addnewmember(addnewsql, name);
    }
    public static void UpdateInformation (String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {

        String currentMember = showInputDialog("Enter memberID for the person who wish to update:");

        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[4];
        options[0] = "Change Password";
        options[1] = "Change payment method";
        options[2] = "Update contact information";
        options[3] = "Back to staff main menu";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(),"Choose what information to update","Update Member Information",0,JOptionPane.INFORMATION_MESSAGE,icon,options,null);
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0 :
                membershipSystem.changePassword(memberID, tier, uname, fnamn, defaultGym);
            case 1 :
                membershipSystem.updatePaymentMethod(memberID);
            case 2 :
                membershipSystem.UpdateInformation(memberID, tier, uname, fnamn, defaultGym);
            case 3 :
                staffView.mainmenu(memberID, tier, uname, fnamn, defaultGym);

        }
    }
    public static void addnewinstruct () throws SQLException {
        System.out.printf("Preparing to add new instructor");

        JTextField instructorID = new JTextField(14);
        JTextField memberID = new JTextField(14);
        JPanel newinstructpanel = new JPanel();
        newinstructpanel.setLayout(new GridLayout(2, 1));

        newinstructpanel.add(new JLabel("New instructorID"));
        newinstructpanel.add(instructorID);
        newinstructpanel.add(Box.createHorizontalStrut(8)); // a spacer
        newinstructpanel.add(new JLabel("memberID"));
        newinstructpanel.add(memberID);

        ImageIcon bild1 = new ImageIcon(fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newinstructpanel, "New instructor", JOptionPane.OK_CANCEL_OPTION, 0, bild1);
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            System.exit(22);
        }

        String instructorIDs = instructorID.getText();
        String memberIDs = memberID.getText();

        String addnewinstructsql = "INSERT INTO instructor" + "(\"instructorID\", \"memberID\")" +
                                                            "VALUES ('"+instructorIDs+"','"+memberIDs+"');";
        System.out.printf(addnewinstructsql);
        sql.addnewinstruct(addnewinstructsql, memberIDs);
    }
    public static void addnewcertificate () {
        //yet to be done...
    }
    public static void createclass () throws SQLException {
        System.out.println("system activated: superbiff 3000 starting...");

        JTextField classID = new JTextField(14);
        JTextField className = new JTextField(14);
        JTextField time = new JTextField(14);
        JTextField date = new JTextField(14);
        JTextField availableSlot = new JTextField(14);
        JTextField instructorID = new JTextField(14);

        JPanel newclassPanel = new JPanel();
        newclassPanel.setLayout(new GridLayout(6,1));

        newclassPanel.add(new JLabel("classID"));
        newclassPanel.add(classID);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newclassPanel.add(new JLabel("Class Name"));
        newclassPanel.add(className);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newclassPanel.add(new JLabel("Time HHMM"));
        newclassPanel.add(time);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newclassPanel.add(new JLabel("Date YYYYMMDD"));
        newclassPanel.add(date);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newclassPanel.add(new JLabel("Available Slots"));
        newclassPanel.add(availableSlot);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newclassPanel.add(new JLabel("instructorID"));
        newclassPanel.add(instructorID);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer

        ImageIcon bild1 = new ImageIcon (fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newclassPanel, "New member", JOptionPane.OK_CANCEL_OPTION,0,bild1);
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            System.exit(22);
        }

        String classIDs = classID.getText();
        String classNames = className.getText();
        String times = time.getText();
        String dates = date.getText();
        String availableSlots = availableSlot.getText();
        String instructorIDs = instructorID.getText();

        String newclasssql = "INSERT INTO class" +
                "(\"classID\", \"className\", \"time\", \"date\", \"availableSlots\", \"InstructorID\")" +
                "VALUES ('"+classIDs+"','"+classNames+"', '"+times+"', '"+dates+"', '"+availableSlots+"', '"+instructorIDs+"');";

        System.out.println(newclasssql);

        sql.createClass(newclasssql);
    }
}

//hej daniel