import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class MainApp extends JFrame {
    private User user;
    private JButton eventCreationButton;
    private JButton attendeeManagementButton;
    private JButton schedulePlanningButton;
    private JButton logoutButton;
    public JPanel contentPanel;

    private Connection connection;

    public MainApp(User user) {
        this.user = user;
        setTitle("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setupUI();
        setVisible(true);

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/eventmanagementsystemdatabase?serverTimezone=UTC", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.black);
        JLabel welcomeLabel = new JLabel("Welcome " + user.name + "!              Event Management System", JLabel.LEFT);
        welcomeLabel.setFont(new Font("Segoe Print", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel);
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black, 0),
                BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        mainPanel.add(separator, BorderLayout.CENTER);

        // Create a navigation bar on the left
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(new Color(225, 235, 251));
        navPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black, 5),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JSeparator separator2 = new JSeparator(JSeparator.VERTICAL);
        separator2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        mainPanel.add(separator2, BorderLayout.CENTER);


        // Create and style buttons
        eventCreationButton = createStyledButton("Event Creation", new ImageIcon("src/assets/event.png"));
        schedulePlanningButton = createStyledButton("Schedule Planning", new ImageIcon("src/assets/schedule.png"));
        attendeeManagementButton = createStyledButton("Attendee Management", new ImageIcon("src/assets/attendees.png"));
        logoutButton = createStyledButton("Log Out", new ImageIcon("src/assets/logout.png"));

        // Add mouse listeners to buttons
        eventCreationButton.addMouseListener(new ButtonMouseListener(eventCreationButton));
        schedulePlanningButton.addMouseListener(new ButtonMouseListener(schedulePlanningButton));
        attendeeManagementButton.addMouseListener(new ButtonMouseListener(attendeeManagementButton));
        logoutButton.addMouseListener(new ButtonMouseListener(logoutButton));

        // Add buttons to the navigation panel
        navPanel.add(eventCreationButton);
        navPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        navPanel.add(schedulePlanningButton);
        navPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        navPanel.add(attendeeManagementButton);
        navPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        navPanel.add(logoutButton);

        // Create a content panel on the right
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(225, 235, 251));

        // Add welcome panel, navigation panel, and content panel to the main panel
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        eventCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel eventCreationPanel = new JPanel(new BorderLayout());
                eventCreationPanel.setBackground(new Color(225, 235, 251));
                eventCreationPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 5),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));

                JLabel titleLabel = new JLabel("Event Creation", JLabel.CENTER);
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                eventCreationPanel.add(titleLabel, BorderLayout.NORTH);

                JPanel fieldsPanel = new JPanel(new GridBagLayout());
                fieldsPanel.setBackground(new Color(225, 235, 251));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel titleLbl = new JLabel("Title:");
                titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(titleLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 0;
                JTextField titleField = new JTextField(20);
                fieldsPanel.add(titleField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel descriptionLbl = new JLabel("Description:");
                descriptionLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(descriptionLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                JTextArea descriptionField = new JTextArea(5, 20);
                descriptionField.setLineWrap(true);
                descriptionField.setWrapStyleWord(true);
                fieldsPanel.add(new JScrollPane(descriptionField), gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                JLabel dateLbl = new JLabel("Date:");
                dateLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(dateLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                JComboBox<LocalDate> dateComboBox = new JComboBox<>();
                fieldsPanel.add(dateComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                JLabel timeLbl = new JLabel("Time:");
                timeLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(timeLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                JComboBox<LocalTime> timeComboBox = new JComboBox<>();
                fieldsPanel.add(timeComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                JLabel codeLbl = new JLabel("Event Code:");
                codeLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(codeLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 4;
                JTextField codeField = new JTextField(10);
                fieldsPanel.add(codeField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                JButton createButton = new JButton("Create Event");
                fieldsPanel.add(createButton, gbc);

                eventCreationPanel.add(fieldsPanel, BorderLayout.CENTER);

                contentPanel.removeAll();
                contentPanel.add(eventCreationPanel, BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();

                // Populate dateComboBox with the next 30 days
                dateComboBox.removeAllItems();
                for (int i = 0; i < 30; i++) {
                    dateComboBox.addItem(LocalDate.now().plusDays(i));
                }

                dateComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeComboBox.removeAllItems();
                        LocalDate today = LocalDate.now();
                        LocalDate selectedDate = (LocalDate) dateComboBox.getSelectedItem();
                        LocalTime now = LocalTime.now();
                        int hour = now.getHour();
                        int minute = now.getMinute();

                        if (selectedDate.equals(today)) {
                            for (int i = hour; i < 24; i++) {
                                if (i == hour) {
                                    if (minute < 30) {
                                        timeComboBox.addItem(LocalTime.of(i, 30));
                                    }
                                } else {
                                    timeComboBox.addItem(LocalTime.of(i, 0));
                                    timeComboBox.addItem(LocalTime.of(i, 30));
                                }
                            }
                        } else {
                            for (int i = 0; i < 24; i++) {
                                timeComboBox.addItem(LocalTime.of(i, 0));
                                timeComboBox.addItem(LocalTime.of(i, 30));
                            }
                        }
                    }
                });

                dateComboBox.setSelectedItem(LocalDate.now());


                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String title = titleField.getText();
                        String description = descriptionField.getText();
                        LocalDate date = (LocalDate) dateComboBox.getSelectedItem();
                        LocalTime time = (LocalTime) timeComboBox.getSelectedItem();
                        String code = codeField.getText().trim();
                        String userEmail = user.email;

                        if (title.isEmpty() || description.isEmpty() || code.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all fields, including the event code.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Event createdEvent = new Event(title, description, String.valueOf(date), String.valueOf(time), code);

                        try {
                            String sql = "INSERT INTO events (event_name, event_description, event_date, event_time, code,creator_email) VALUES (?, ?, ?, ?, ?,?)";
                            PreparedStatement pstmt = connection.prepareStatement(sql);
                            pstmt.setString(1, createdEvent.getTitle());
                            pstmt.setString(2, createdEvent.getDescription());
                            pstmt.setString(3, createdEvent.getDate());
                            pstmt.setString(4, createdEvent.getTime());
                            pstmt.setString(5, createdEvent.getCode()); // Insert the event code
                            pstmt.setString(6, userEmail);
                            pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Event created successfully!");

                            titleField.setText("");
                            descriptionField.setText("");
                            dateComboBox.setSelectedIndex(0);
                            timeComboBox.setSelectedIndex(0);
                            codeField.setText("");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to create event. Your event code may be already used!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

            }
        });


        schedulePlanningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new panel for schedule planning
                JPanel schedulePlanningPanel = new JPanel(new BorderLayout());
                schedulePlanningPanel.setBackground(new Color(225, 235, 251));
                schedulePlanningPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 5),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));

                JLabel titleLabel = new JLabel("Schedule Planning", JLabel.CENTER);
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                schedulePlanningPanel.add(titleLabel, BorderLayout.NORTH);

                JPanel fieldsPanel = new JPanel(new GridBagLayout());
                fieldsPanel.setBackground(new Color(225, 235, 251));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel dateLabel = new JLabel("Date:");
                dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(dateLabel, gbc);
                gbc.gridx = 1;
                gbc.gridy = 0;
                JComboBox<LocalDate> dateComboBox = new JComboBox<>();
                LocalDate currentDate = LocalDate.now();
                for (int i = 0; i < 30; i++) {
                    dateComboBox.addItem(currentDate.plusDays(i));
                }
                fieldsPanel.add(dateComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel startTimeLabel = new JLabel("Start Time:");
                startTimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(startTimeLabel, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                JComboBox<LocalTime> startTimeComboBox = new JComboBox<>();
                LocalTime currentTime = LocalTime.of(8, 0);
                for (int i = 0; i < 24; i++) {
                    startTimeComboBox.addItem(currentTime.plusHours(i));
                }
                fieldsPanel.add(startTimeComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                JLabel endTimeLabel = new JLabel("End Time:");
                endTimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(endTimeLabel, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                JComboBox<LocalTime> endTimeComboBox = new JComboBox<>();
                currentTime = LocalTime.of(8, 0);
                for (int i = 0; i < 24; i++) {
                    endTimeComboBox.addItem(currentTime.plusHours(i));
                }
                fieldsPanel.add(endTimeComboBox, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                JLabel codeLabel = new JLabel("Event Code:");
                codeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(codeLabel, gbc);
                gbc.gridx = 1;
                gbc.gridy = 3;
                JTextField codeField = new JTextField(10);
                fieldsPanel.add(codeField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 2;
                JButton planButton = new JButton("Plan Schedule");
                fieldsPanel.add(planButton, gbc);

                schedulePlanningPanel.add(fieldsPanel, BorderLayout.CENTER);

                // Add the schedule planning panel to the content panel
                contentPanel.removeAll();
                contentPanel.add(schedulePlanningPanel, BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();

                planButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get the selected schedule details
                        LocalDate date = (LocalDate) dateComboBox.getSelectedItem();
                        LocalTime startTime = (LocalTime) startTimeComboBox.getSelectedItem();
                        LocalTime endTime = (LocalTime) endTimeComboBox.getSelectedItem();
                        String eventCode = codeField.getText().trim();
                        String userEmail = user.email;

                        // Validate the times
                        if (startTime.equals(endTime)) {
                            JOptionPane.showMessageDialog(null, "Start time and end time cannot be the same.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (endTime.isBefore(startTime)) {
                            JOptionPane.showMessageDialog(null, "End time cannot be earlier than start time.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Convert LocalDate and LocalTime to SQL format
                        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
                        java.sql.Time sqlStartTime = java.sql.Time.valueOf(startTime);
                        java.sql.Time sqlEndTime = java.sql.Time.valueOf(endTime);

                        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagementsystemdatabase", "root", "")) {
                            // Check if the event code exists and matches the date
                            String checkEventSql = "SELECT COUNT(*) FROM events WHERE code = ? AND event_date = ?";
                            try (PreparedStatement checkEventPstmt = conn.prepareStatement(checkEventSql)) {
                                checkEventPstmt.setString(1, eventCode);
                                checkEventPstmt.setDate(2, sqlDate);
                                ResultSet eventRs = checkEventPstmt.executeQuery();
                                if (!eventRs.next() || eventRs.getInt(1) == 0) {
                                    JOptionPane.showMessageDialog(null, "Invalid event code or event date.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            String checkEventTimeSql = "SELECT event_time FROM events WHERE code = ?";
                            try (PreparedStatement checkEventtimePstmt = conn.prepareStatement(checkEventTimeSql)) {
                                checkEventtimePstmt.setString(1, eventCode);
                                ResultSet eventRs = checkEventtimePstmt.executeQuery();

                                if (eventRs.next()) {
                                    java.sql.Time eventTime = eventRs.getTime("event_time");
                                    if (sqlStartTime.before(eventTime)) {
                                        JOptionPane.showMessageDialog(null, "The event start time cannot be earlier than the event time.", "Error", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }

                            String checkAttendeeSql = "SELECT COUNT(*) FROM attendees WHERE event_code = ? AND email = ?";
                            try (PreparedStatement checkAttendeePstmt = conn.prepareStatement(checkAttendeeSql)) {
                                checkAttendeePstmt.setString(1, eventCode);
                                checkAttendeePstmt.setString(2, userEmail);
                                ResultSet attendeeRs = checkAttendeePstmt.executeQuery();
                                if (!attendeeRs.next() || attendeeRs.getInt(1) == 0) {
                                    JOptionPane.showMessageDialog(null, "You are not an attendee of this event.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            // Check for schedule conflicts
                            String conflictSql = "SELECT COUNT(*) FROM schedule_plans WHERE user_email = ? AND schedule_date = ? AND ((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?) OR (start_time >= ? AND end_time <= ?))";
                            try (PreparedStatement conflictPstmt = conn.prepareStatement(conflictSql)) {
                                conflictPstmt.setString(1, userEmail);
                                conflictPstmt.setDate(2, sqlDate);
                                conflictPstmt.setTime(3, sqlStartTime);
                                conflictPstmt.setTime(4, sqlStartTime);
                                conflictPstmt.setTime(5, sqlEndTime);
                                conflictPstmt.setTime(6, sqlEndTime);
                                conflictPstmt.setTime(7, sqlStartTime);
                                conflictPstmt.setTime(8, sqlEndTime);
                                ResultSet conflictRs = conflictPstmt.executeQuery();
                                if (conflictRs.next() && conflictRs.getInt(1) > 0) {
                                    JOptionPane.showMessageDialog(null, "Schedule conflict detected.", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            // Insert the schedule plan into the database
                            String insertSql = "INSERT INTO schedule_plans (user_email, schedule_date, start_time, end_time, event_code) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                                insertPstmt.setString(1, userEmail);
                                insertPstmt.setDate(2, sqlDate);
                                insertPstmt.setTime(3, sqlStartTime);
                                insertPstmt.setTime(4, sqlEndTime);
                                insertPstmt.setString(5, eventCode);
                                insertPstmt.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Schedule planned successfully!");

                                // Clear the input fields
                                codeField.setText("");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to plan the schedule.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                JButton viewScheduleButton = new JButton("View Schedule");
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                fieldsPanel.add(viewScheduleButton, gbc);

                viewScheduleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Create a new panel for viewing the schedule
                        JPanel viewSchedulePanel = new JPanel(new BorderLayout());
                        viewSchedulePanel.setBackground(new Color(225, 235, 251));
                        viewSchedulePanel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.black, 5),
                                BorderFactory.createEmptyBorder(5, 10, 5, 10)
                        ));

                        JLabel titleLabel = new JLabel("View Schedule", JLabel.CENTER);
                        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                        viewSchedulePanel.add(titleLabel, BorderLayout.NORTH);

                        // Create a table model and table to display the schedule
                        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Date", "Start Time", "End Time", "Event Code", "Remove Event"}, 0);
                        JTable table = new JTable(tableModel);
                        JScrollPane scrollPane = new JScrollPane(table);
                        viewSchedulePanel.add(scrollPane, BorderLayout.CENTER);

                        // Custom cell renderer for buttons
                        table.getColumn("Remove Event").setCellRenderer(new ButtonRenderer());
                        table.getColumn("Remove Event").setCellEditor(new ButtonEditor(new JCheckBox(), user.email, tableModel, table));

                        // Fetch the schedule from the database and populate the table
                        String sql = "SELECT schedule_date, start_time, end_time, event_code FROM schedule_plans WHERE user_email = ?";
                        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagementsystemdatabase", "root", "");
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                            pstmt.setString(1, user.email);
                            ResultSet rs = pstmt.executeQuery();

                            while (rs.next()) {
                                java.sql.Date date = rs.getDate("schedule_date");
                                java.sql.Time startTime = rs.getTime("start_time");
                                java.sql.Time endTime = rs.getTime("end_time");
                                String eventCode = rs.getString("event_code");

                                tableModel.addRow(new Object[]{
                                        date.toLocalDate(),
                                        startTime.toLocalTime(),
                                        endTime.toLocalTime(),
                                        eventCode,
                                        "Remove"
                                });
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to fetch schedule.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        contentPanel.removeAll();
                        contentPanel.add(viewSchedulePanel, BorderLayout.CENTER);
                        contentPanel.revalidate();
                        contentPanel.repaint();
                    }
                });
            }
        });



        attendeeManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel attendeeManagementPanel = new JPanel(new BorderLayout());
                attendeeManagementPanel.setBackground(new Color(225, 235, 251));
                attendeeManagementPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 5),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));

                JLabel titleLabel = new JLabel("Attendee Management", JLabel.CENTER);
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                attendeeManagementPanel.add(titleLabel, BorderLayout.NORTH);

                JPanel fieldsPanel = new JPanel(new GridBagLayout());
                fieldsPanel.setBackground(new Color(225, 235, 251));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

                gbc.gridx = 0;
                gbc.gridy = 0;
                JLabel attendeeNameLbl = new JLabel("Name:");
                attendeeNameLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(attendeeNameLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 0;
                JTextField attendeeNameField = new JTextField(20);
                fieldsPanel.add(attendeeNameField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                JLabel attendeeEmailLbl = new JLabel("Email:");
                attendeeEmailLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(attendeeEmailLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 1;
                JTextField attendeeEmailField = new JTextField(20);
                fieldsPanel.add(attendeeEmailField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 2;
                JLabel codeLbl = new JLabel("Event Code:");
                codeLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
                fieldsPanel.add(codeLbl, gbc);
                gbc.gridx = 1;
                gbc.gridy = 2;
                JTextField codeField = new JTextField(10);
                fieldsPanel.add(codeField, gbc);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                JButton addButton = new JButton("Add Attendee");
                fieldsPanel.add(addButton, gbc);

                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 2;
                JButton viewButton = new JButton("View Attendees");
                fieldsPanel.add(viewButton, gbc);

                attendeeManagementPanel.add(fieldsPanel, BorderLayout.CENTER);

                contentPanel.removeAll();
                contentPanel.add(attendeeManagementPanel, BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = attendeeNameField.getText();
                        String email = attendeeEmailField.getText();
                        String eventCode = codeField.getText();
                        String userEmail = user.email;

                        if (name.isEmpty() || email.isEmpty() || eventCode.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                            return;
                        }

                        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagementsystemdatabase", "root", "")) {
                            // Check if the user is the creator of the event
                            String checkCreatorSql = "SELECT COUNT(*) FROM events WHERE code = ? AND creator_email = ?";
                            PreparedStatement checkCreatorPstmt = connection.prepareStatement(checkCreatorSql);
                            checkCreatorPstmt.setString(1, eventCode);
                            checkCreatorPstmt.setString(2, userEmail);
                            ResultSet checkCreatorRs = checkCreatorPstmt.executeQuery();
                            checkCreatorRs.next();
                            int isCreator = checkCreatorRs.getInt(1);

                            if (isCreator == 0) {
                                JOptionPane.showMessageDialog(null, "Only the event creator can add attendees.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Check if the event code exists
                            String checkEventSql = "SELECT COUNT(*) FROM events WHERE code = ?";
                            PreparedStatement checkEventPstmt = connection.prepareStatement(checkEventSql);
                            checkEventPstmt.setString(1, eventCode);
                            ResultSet checkEventRs = checkEventPstmt.executeQuery();
                            checkEventRs.next();
                            int eventCount = checkEventRs.getInt(1);

                            if (eventCount == 0) {
                                JOptionPane.showMessageDialog(null, "Event code does not exist. Cannot add attendee.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Check if the user is registered in the users table
                            String checkUserSql = "SELECT COUNT(*) FROM users WHERE name = ? AND email = ?";
                            PreparedStatement checkUserPstmt = connection.prepareStatement(checkUserSql);
                            checkUserPstmt.setString(1, name);
                            checkUserPstmt.setString(2, email);
                            ResultSet checkUserRs = checkUserPstmt.executeQuery();
                            checkUserRs.next();
                            int userCount = checkUserRs.getInt(1);

                            if (userCount > 0) {
                                // Check if the attendee is already added to the event
                                String checkAttendeeSql = "SELECT COUNT(*) FROM attendees WHERE name = ? AND email = ? AND event_code = ?";
                                PreparedStatement checkAttendeePstmt = connection.prepareStatement(checkAttendeeSql);
                                checkAttendeePstmt.setString(1, name);
                                checkAttendeePstmt.setString(2, email);
                                checkAttendeePstmt.setString(3, eventCode);
                                ResultSet checkAttendeeRs = checkAttendeePstmt.executeQuery();
                                checkAttendeeRs.next();
                                int attendeeCount = checkAttendeeRs.getInt(1);

                                if (attendeeCount == 0) {
                                    // If the user is registered and not already added to the event, add them
                                    String sql = "INSERT INTO attendees (name, email, event_code) VALUES (?, ?, ?)";
                                    PreparedStatement pstmt = connection.prepareStatement(sql);
                                    pstmt.setString(1, name);
                                    pstmt.setString(2, email);
                                    pstmt.setString(3, eventCode);
                                    pstmt.executeUpdate();

                                    JOptionPane.showMessageDialog(null, "Attendee added successfully!");
                                    attendeeNameField.setText("");
                                    attendeeEmailField.setText("");
                                    codeField.setText("");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Attendee already added to this event.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "User not registered. Cannot add attendee.");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to add attendee.");
                        }
                    }
                });


                viewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String eventCode = codeField.getText().trim();
                        String userEmail = user.email;

                        if (eventCode.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please enter an event code to view attendees.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagementsystemdatabase", "root", "")) {
                            // Check if the user is the creator of the event
                            String checkCreatorSql = "SELECT COUNT(*) FROM events WHERE code = ? AND creator_email = ?";
                            PreparedStatement checkCreatorPstmt = connection.prepareStatement(checkCreatorSql);
                            checkCreatorPstmt.setString(1, eventCode);
                            checkCreatorPstmt.setString(2, userEmail);
                            ResultSet checkCreatorRs = checkCreatorPstmt.executeQuery();
                            checkCreatorRs.next();
                            int isCreator = checkCreatorRs.getInt(1);

                            if (isCreator == 0) {
                                JOptionPane.showMessageDialog(null, "Only the event creator can view attendees.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Check if the event code exists
                            String checkEventSql = "SELECT COUNT(*) FROM events WHERE code = ?";
                            PreparedStatement checkEventPstmt = connection.prepareStatement(checkEventSql);
                            checkEventPstmt.setString(1, eventCode);
                            ResultSet checkEventRs = checkEventPstmt.executeQuery();
                            checkEventRs.next();
                            int eventCount = checkEventRs.getInt(1);

                            if (eventCount == 0) {
                                JOptionPane.showMessageDialog(null, "Event code does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Create panel for viewing attendees
                            JPanel viewAttendeesPanel = new JPanel(new BorderLayout());
                            viewAttendeesPanel.setBackground(new Color(225, 235, 251));
                            viewAttendeesPanel.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.black, 5),
                                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                            ));

                            JLabel titleLabel = new JLabel("Attendees List", JLabel.CENTER);
                            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                            viewAttendeesPanel.add(titleLabel, BorderLayout.NORTH);

                            String[] columnNames = {"Name", "Email", "Event Code", "Remove"};
                            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                            JTable table = new JTable(tableModel);
                            table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
                            table.getColumn("Remove").setCellEditor(new AttendeeButtonEditor(new JCheckBox(), userEmail, tableModel, table));
                            JScrollPane scrollPane = new JScrollPane(table);
                            viewAttendeesPanel.add(scrollPane, BorderLayout.CENTER);

                            // Fetch attendees from database and add to table model
                            String sql = "SELECT * FROM attendees WHERE event_code = ?";
                            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                                pstmt.setString(1, eventCode);
                                ResultSet rs = pstmt.executeQuery();

                                while (rs.next()) {
                                    String name = rs.getString("name");
                                    String email = rs.getString("email");
                                    tableModel.addRow(new Object[]{name, email, eventCode, "Remove"});
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Failed to fetch attendees.", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            contentPanel.removeAll();
                            contentPanel.add(viewAttendeesPanel, BorderLayout.CENTER);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Failed to check event code.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }});


                logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutAction();
            }
        });
    }



    private JButton createStyledButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(225, 235, 251));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black, 3),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setPreferredSize(new Dimension(225, 40));
        button.setMinimumSize(new Dimension(225, 40));
        button.setMaximumSize(new Dimension(225, 40));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setIcon(icon);
        button.setIconTextGap(10);
        return button;
    }

    private void logoutAction() {
        // Close database connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                    new LogIn(null);
                }
    }

    private static class ButtonMouseListener extends MouseAdapter {
        private JButton button;

        public ButtonMouseListener(JButton button) {
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(200, 225, 250)); // Change color on hover
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(225, 235, 251));
        }
    }
}
