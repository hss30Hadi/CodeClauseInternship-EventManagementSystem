import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class LogIn extends JDialog {
    private JTextField emailTextField;
    private JButton logInButton;
    private JButton ToRegisterButton;
    private JPanel LogInPanel;
    private JPasswordField passwordField1;
    User user;

    public LogIn(JFrame parent) {
        super(parent);
        setTitle("Log In");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(LogInPanel);
        setMinimumSize(new Dimension(600, 475));
        setModal(true);
        setLocationRelativeTo(parent);
        setResizable(false);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInAction();
            }
        });

        emailTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    logInAction();
                }
            }
        });

        passwordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    logInAction();
                }
            }
        });

        ToRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor(LogIn.this);
                JFrame parentFrame = (JFrame) window.getOwner();
                Register register = new Register(parentFrame);
                register.setVisible(true);

                User user = register.getUser();
                if (user != null) {
                    JOptionPane.showMessageDialog(parentFrame, "New user: " + user.name, "Successful Registration", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    private void logInAction() {
        String email = emailTextField.getText();
        String password = new String(passwordField1.getPassword());

        user = getAuthenticatorUser(email, password);
        if (user != null) {
            dispose();
            new MainApp(user); // Open the main application window upon successful login
        } else {
            ImageIcon icon = new ImageIcon("src/assets/error.png");
            JOptionPane.showMessageDialog(LogIn.this,
                    "<html><body>"
                            + "<div style='font-size:16px; font-weight:bold; color:#FF0000;'>Invalid Email or Password</div>"
                            + "<div style='margin-top:10px; font-size:10px;'>Please check your credentials and try again.</div>"
                            + "</body></html>",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE,
                    icon);
        }
    }

    private User getAuthenticatorUser(String email, String password) {
        User user = null;

        final String DB_url = "jdbc:mysql://localhost/eventmanagementsystemdatabase?serverTimezone=UTC";
        final String DB_username = "root";
        final String DB_password = "";

        try {
            Connection conn = DriverManager.getConnection(DB_url, DB_username, DB_password);

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.name = rs.getString("name");
                user.email = rs.getString("email");
                user.password = rs.getString("password");
            }

            rs.close();
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
