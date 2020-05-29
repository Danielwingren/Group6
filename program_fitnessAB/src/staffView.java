import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.*;

import static javax.swing.JOptionPane.*;

public class staffView {
    public static final String DB_URL = "jdbc:sqlite:db_fitnessAB.db"; // Sökväg till SQLite-databas. Denna bör nu vara relativ så att den fungerar för oss alla i gruppen!
    public static final String DRIVER = "org.sqlite.JDBC";
    static Connection conn = null;

    public static void mainmenu(String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/logo_greeen.png"));
        tier = sql.GetTier(uname);
        while (true) {

            JFrame frame = new JFrame();
            String[] options = new String[5];
            options[0] = "Add new member";
            options[4] = "Logout";
            options[1] = "Add new Instructor";
            options[2] = "Manage classes";
            options[3] = "Check inventory";
            int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Welcome " + fnamn + ", please choose operation below:", "Main Menu", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
            if (val == JOptionPane.CLOSED_OPTION) {
                System.exit(11);
            }
            switch (val) {
                case 0:
                    addnewmember(memberID, tier, fnamn, uname, defaultGym);
                    break;
                case 4:
                    fitnessAB.login();
                    break;
                case 1:
                    addnewinstruct(memberID, tier, uname, fnamn, defaultGym);
                    break;
                case 2:
                    manageClasses(memberID, tier, fnamn, uname, defaultGym);
                    break;
                case 3 :
                    inventory(memberID, tier, fnamn, uname, defaultGym);
            }
        }
    }

    public static void addnewmember(String xmemberID, int xtier, String xfnamn, String xuname, String xdefaultGym) throws SQLException {
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
        newmemberPanel.setLayout(new GridLayout(12, 1));
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


        ImageIcon bild1 = new ImageIcon(fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newmemberPanel, "New member", JOptionPane.OK_CANCEL_OPTION, 0, bild1);
        if (result == JOptionPane.CANCEL_OPTION) {
            staffView.mainmenu(xmemberID, xtier, xfnamn, xuname, xdefaultGym);
        } else if (result == JOptionPane.CLOSED_OPTION) {
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
                "VALUES ('" + memberIDs + "','" + fNames + "', '" + lNames + "', '" + cardNumbers + "', '" + tiers + "', '" + phones + "', '" + emails + "', '" + passwords + "', '" + creditCards + "', '" + personNummers + "', '" + defaultGyms + "');";
        System.out.println(addnewsql);
        String name = fNames + " " + lNames;
        sql.addnewmember(addnewsql, name);
    }

    public static void UpdateInformation(String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {

        String currentMember = showInputDialog("Enter memberID for the person who wish to update:");

        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        JFrame frame = new JFrame();
        String[] options = new String[4];
        options[0] = "Change Password";
        options[1] = "Change payment method";
        options[2] = "Update contact information";
        options[3] = "Back to staff main menu";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Choose what information to update", "Update Member Information", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
        if (val == JOptionPane.CLOSED_OPTION) {
            System.exit(11);
        }
        switch (val) {
            case 0:
                membershipSystem.changePassword(memberID, tier, uname, fnamn, defaultGym);
            case 1:
                membershipSystem.updatePaymentMethod(memberID);
            case 2:
                membershipSystem.UpdateContactInformation(memberID, tier, uname, fnamn, defaultGym);
            case 3:
                staffView.mainmenu(memberID, tier, uname, fnamn, defaultGym);

        }
    }

    public static void addnewinstruct(String memberID, int tier, String uname, String fnamn, String defaultGym) throws SQLException {
        System.out.printf("Preparing to add new instructor");

        JTextField instructorID = new JTextField(14);
        JTextField memberIDx = new JTextField(14);
        JPanel newinstructpanel = new JPanel();
        newinstructpanel.setLayout(new GridLayout(2, 1));

        newinstructpanel.add(new JLabel("New instructorID"));
        newinstructpanel.add(instructorID);
        newinstructpanel.add(Box.createHorizontalStrut(8)); // a spacer
        newinstructpanel.add(new JLabel("memberID"));
        newinstructpanel.add(memberIDx);

        ImageIcon bild1 = new ImageIcon(fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newinstructpanel, "New instructor", JOptionPane.OK_CANCEL_OPTION, 0, bild1);
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            mainmenu(memberID, tier, uname, fnamn, defaultGym);
        }

        String instructorIDs = instructorID.getText();
        String memberIDs = memberIDx.getText();

        String addnewinstructsql = "INSERT INTO instructor" + "(\"instructorID\", \"memberID\")" +
                "VALUES ('" + instructorIDs + "','" + memberIDs + "');";
        System.out.printf(addnewinstructsql);
        try {
            sql.addnewinstruct(addnewinstructsql, memberIDs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createclass(String memberID, int tier,String uname,String fnamn,String defaultGym) throws SQLException {
        System.out.println("system activated: superbiff 3000 starting...");

        String[] classchoices = { "spinning3000","yogaCalm", "coreExtreme","coreStatic","spinning2000","yogaPower","stepUp","stepQuick","boxingZ","challengeUltimate","stepInsane"};
        final JComboBox<String> className = new JComboBox<>(classchoices);
        String[] instructorchoices = { "John","Maja", "Daniel","Emil","Moa"};
        final JComboBox<String> instructorname = new JComboBox<>(instructorchoices);
        String[] roomchoices = { "11","12","21","22","31","32"};
        final JComboBox<String> roomID = new JComboBox<>(roomchoices);

        JTextField time = new JTextField(14);
        JTextField date = new JTextField(14);
        JTextField availableSlot = new JTextField(14);

        JPanel newclassPanel = new JPanel();
        newclassPanel.setLayout(new GridLayout(7, 1));

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
        newclassPanel.add(new JLabel("Instructor"));
        newclassPanel.add(instructorname);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer
        newclassPanel.add(new JLabel("roomID"));
        newclassPanel.add(roomID);
        newclassPanel.add(Box.createHorizontalStrut(8)); // a spacer

        ImageIcon bild1 = new ImageIcon(fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newclassPanel, "New class", JOptionPane.OK_CANCEL_OPTION, 0, bild1);
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
            staffView.manageClasses(memberID, tier, uname, fnamn, defaultGym);
        }

        String instructor = String.valueOf(instructorname.getSelectedItem());
        System.out.println(instructor);
        String instructorIDs = null;
        try {
            instructorIDs = sql.sqlinstructorID(instructor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String classNames = String.valueOf(className.getSelectedItem());
        String roomIDs = String.valueOf(roomID.getSelectedItem());
        String times = time.getText();
        String dates = date.getText();
        String availableSlots = availableSlot.getText();
        String classIDs = dates+times+roomIDs;

        String newclasssql = "INSERT INTO class" +
                "(\"classID\", \"className\", \"time\", \"date\", \"availableSlots\", \"InstructorID\", \"roomID\")" +
                "VALUES ('" + classIDs + "','" + classNames + "', '" + times + "', '" + dates + "', '" + availableSlots + "', '" + instructorIDs + "', '" + roomIDs + "');";

        System.out.println(newclasssql);

        sql.createClass(newclasssql);
    }

    public static void manageClasses(String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {

        JFrame frame = new JFrame();
        String[] options = new String[5];
        options[1] = "Add new class";
        options[0] = "Create new class";
        options[2] = "Remove class";
        options[3] = "Edit class description";
        options[4] = "Back";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Which operation would you like to perform?", "Class management menu ", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (val == JOptionPane.CLOSED_OPTION) {
            mainmenu(memberID, tier, fnamn, uname, defaultGym);
        }
        switch (val) {
            case 1:
                createclass(memberID, tier, uname, fnamn, defaultGym);
                break;
            case 0:
                addNewClass(memberID, tier, fnamn, uname, defaultGym);
                break;
            case 2:
                removeClass();
                break;
            case 3:
                editClassDescription(memberID, tier, fnamn, uname, defaultGym);
                break;
            case 4:
                mainmenu(memberID,tier,fnamn,uname,defaultGym);
                break;
        }
    }

    public static void addNewClass(String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        JTextField name = new JTextField(14);
        JTextField type = new JTextField(14);
        JTextField description = new JTextField(14);
        JTextField length = new JTextField(14);
        JPanel newclassnamePanel = new JPanel();
        newclassnamePanel.setLayout(new GridLayout(14, 1));
        newclassnamePanel.add(new JLabel("Class name"));
        newclassnamePanel.add(name);
        newclassnamePanel.add(Box.createHorizontalStrut(8));
        newclassnamePanel.add(new JLabel("Class type"));
        newclassnamePanel.add(type);
        newclassnamePanel.add(Box.createHorizontalStrut(8));
        newclassnamePanel.add(new JLabel("Class description"));
        newclassnamePanel.add(description);
        newclassnamePanel.add(Box.createHorizontalStrut(8));
        newclassnamePanel.add(new JLabel("Class length"));
        newclassnamePanel.add(length);
        newclassnamePanel.add(Box.createHorizontalStrut(8));
        ImageIcon bild = new ImageIcon(fitnessAB.class.getResource("images/login.png"));
        int result = JOptionPane.showConfirmDialog(null, newclassnamePanel, "Add new class", JOptionPane.OK_CANCEL_OPTION, 0, bild);
        if (result == JOptionPane.CANCEL_OPTION) {
            staffView.manageClasses(memberID, tier, fnamn, uname, defaultGym);
        } else if (result == JOptionPane.CLOSED_OPTION) {
            System.exit(22);
        }
        String newname = name.getText();
        String newtype = type.getText();
        String newdescription = description.getText();
        String newlength = length.getText();
        String addnewsql = "INSERT INTO classtype" + "(\"className\", \"classType\", \"description\", \"length\")" + "VALUES ('" + newname + "', '" + newtype + "', '" + newdescription + "', '" + newlength + "');";
        System.out.println(addnewsql);
        sql.addClass(addnewsql, newname);
        manageClasses(memberID, tier, fnamn, uname, defaultGym);
    }
    
    public static void editClassDescription(String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        String classname = showInputDialog("Enter the classname of the class you want to update:");

        conn = sql.dbconnection();
        String query = ("select description from classtype where className = '" + classname + "';");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        String description = "";
        try {
            if(rs.next() == false){
                showMessageDialog(null, "This class does not exist");
                manageClasses(memberID, tier, fnamn, uname, defaultGym);
            }
            description = rs.getString("description");
            String newdescription = JOptionPane.showInputDialog("Edit the description: ", description);
            if(newdescription == null){
                manageClasses(memberID, tier, fnamn, uname, defaultGym);
            }
            String updatesql = ("update classtype set description = '" + newdescription + "' where className = '" + classname + "';");
            stmt.executeUpdate(updatesql);
            showMessageDialog(null, "The description has been updated.");
        } catch (SQLException e) {
            showMessageDialog(null, "Something went wrong.");
            System.out.println(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            rs.close();
            conn.close();
            manageClasses(memberID, tier, fnamn, uname, defaultGym);
        }
    }
    public static void inventory (String memberID, int tier, String fnamn, String uname, String defaultGym) throws SQLException {
        ImageIcon icon = new ImageIcon(fitnessAB.class.getResource("images/settings.png"));
        String gym = "";

        JFrame frame = new JFrame();
        String[] options = new String[3];
        options[0] = "Hisingen";
        options[1] = "Bergsjön";
        options[2] = "Långedrag";
        int val = JOptionPane.showOptionDialog(frame.getContentPane(), "Which gym would you like to see inventory for?", "Inventory", 0, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
        if (val == JOptionPane.CLOSED_OPTION) {
            staffView.mainmenu(memberID, tier, fnamn, uname, defaultGym);
        }
        switch (val) {
            case 0:
                gym = "1";
                break;
            case 1:
                gym = "2";
                break;
            case 2:
                gym = "3";
                break;
        }
        ResultSet rs = sql.getInventory(gym);
        String classes;
        String classesx = "";
        String message = "Equipment: | Quantity: |\n";
        while (rs.next()) {
            String classID = rs.getString(1);
            int classname = rs.getInt(2) ;
            classes = (classID + " |\t "+ classname + "\n");
            classesx = classesx +"\t"+ classes;
        }
        String result = message + classesx;
        showMessageDialog(null,result,"Inventory",PLAIN_MESSAGE,null);
    }

    public static void removeClass() {

    }
}

// Delete class som inte används

/*
    // Delete class: skriva in existerande classname
    public static void deleteClass(String memberID, int tier, String fnamn, String uname, String defaultGym)throws SQLException{
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        Object[] options = {"spinning3000", "yogaCalm", "coreExtreme"};

        Object selectionObject = JOptionPane.showInputDialog(frame, "Choose", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        String selectedClassName = selectionObject.toString();
        "delete from classtype where classname = " + selectedClassName + ";"""
        classbooking.memberscreen(memberID, tier, uname, fnamn, defaultGym);
    }
 */

// Arraylist som inte används nedan

        /* ArrayList<Classtype> classtypelist = new ArrayList<Classtype>();

        try {
            while (rs.next()) {
                Classtype classtypes = new Classtype();
                classtypes.setClassName(rs.getString("ClassName"));
                classtypelist.add(classtypes);
            }
            showMessageDialog(null, (classtypelist.get(1).getClassName()) + "\n" + (classtypelist.get(2).getClassName()) + "\n" + (classtypelist.get(3).getClassName()) + "\n" + (classtypelist.get(4).getClassName()) + "\n" + (classtypelist.get(5).getClassName()));

        } catch (SQLException e) {
            showMessageDialog(null, "Fel!");
            System.out.println(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            rs.close();
            conn.close();
        }
    }

    public static class Classtype {
        private String classname;
        private String description;

        public String getClassName(){
            return classname;
        }
        public void setClassName(String classname) {
            this.classname = classname;
        }
        public String getDescription(){
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }*/