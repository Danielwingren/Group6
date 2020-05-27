import javax.swing.*;
import java.time.LocalDateTime;

public class tidDatum {
    public static void main(String[] args) {
        LocalDateTime datum = LocalDateTime.now();
        System.out.println(datum);
        JOptionPane.showMessageDialog(null,datum);

    }
}