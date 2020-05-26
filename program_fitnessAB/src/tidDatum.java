import javax.swing.*;
import java.time.LocalDate;

public class tidDatum {
    public static void main(String[] args) {
        LocalDate datum = LocalDate.now();
        System.out.println(datum);
        JOptionPane.showMessageDialog(null,datum);
    }
}