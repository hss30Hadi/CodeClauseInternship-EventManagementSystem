import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("");
        setSize(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                LogIn login = new LogIn(mainFrame);

                User user = login.user;
                if (user != null) {
                    System.out.println("Login Successful");
                    System.out.println("Name: " + user.name);
                    System.out.println("Email: " + user.email);
                    System.out.println("Password: " + user.password);
                    mainFrame.setVisible(false);
                } else {
                    System.out.println("Login Unsuccessful");
                    System.exit(0); //exits app if login unsuccessful
                }
            }
        });
    }
}
