import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Register extends JDialog {
    private JTextField nameTxtField;
    private JTextField EmailTxtField;
    private JPasswordField passwordTxtField;
    private JPasswordField passwordConfirmationtxtfield;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel Name;
    private JLabel Email;
    private JLabel Password;
    private JLabel ConfirmPassword;
    private JPanel RegisterPanel;
    public User user;

    public Register(JFrame parent) {
        super(parent);
        setTitle("Create a new account");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(550, 475));
        setModal(true);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    registerUser();
                }
            }
        };

        nameTxtField.addKeyListener(enterKeyAdapter);
        EmailTxtField.addKeyListener(enterKeyAdapter);
        passwordTxtField.addKeyListener(enterKeyAdapter);
        passwordConfirmationtxtfield.addKeyListener(enterKeyAdapter);

        setVisible(true);
    }

    private void registerUser() {
        String name = nameTxtField.getText();
        String email = EmailTxtField.getText();
        String password = String.valueOf(passwordTxtField.getPassword());
        String confirmPassword = String.valueOf(passwordConfirmationtxtfield.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields", "Try Again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Try Again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(name, email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed", "Try Again", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User addUserToDatabase(String name, String email, String password) {
        User user = null;
        final String DB_url = "jdbc:mysql://localhost/eventmanagementsystemdatabase?serverTimezone=UTC";
        final String DB_username = "root";
        final String DB_password = "";

        try {
            Connection conn = DriverManager.getConnection(DB_url, DB_username, DB_password);

            String sql = "INSERT INTO users(name, email, password) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.password = password;
            }

            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    public User getUser() {
        return user;
    }

    public static void main(String[] args) {
        Register register = new Register(null);
        User user = register.user;

        if (user != null) {
            System.out.println("Registration Successful of " + user.name);
        } else {
            System.out.println("Registration Unsuccessful");
        }
    }
}
