import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.Database;
import view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        Database.initialize();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // Use default look and feel if system look and feel fails
                    System.out.println("Could not set system look and feel, using default");
                }

                new LoginFrame().setVisible(true);
            }
        });
    }
}