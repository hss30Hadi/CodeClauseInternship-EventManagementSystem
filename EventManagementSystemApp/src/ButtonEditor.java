import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.time.*;

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private String userEmail;
    private DefaultTableModel tableModel;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, String userEmail, DefaultTableModel tableModel, JTable table) {
        super(checkBox);
        this.userEmail = userEmail;
        this.tableModel = tableModel;
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() > 0) {
                    fireEditingStopped();
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            int row = table.getSelectedRow();
            if (row >= 0 && row < tableModel.getRowCount()) {
                String eventCode = tableModel.getValueAt(row, 3).toString();
                LocalDate date = LocalDate.parse(tableModel.getValueAt(row, 0).toString());
                LocalTime startTime = LocalTime.parse(tableModel.getValueAt(row, 1).toString());
                LocalTime endTime = LocalTime.parse(tableModel.getValueAt(row, 2).toString());

                // Convert to java.sql.Date and java.sql.Time
                java.sql.Date sqlDate = java.sql.Date.valueOf(date);
                java.sql.Time sqlStartTime = java.sql.Time.valueOf(startTime);
                java.sql.Time sqlEndTime = java.sql.Time.valueOf(endTime);

                // Log values for debugging
                System.out.println("Removing event:");
                System.out.println("User Email: " + userEmail);
                System.out.println("Date: " + sqlDate);
                System.out.println("Start Time: " + sqlStartTime);
                System.out.println("End Time: " + sqlEndTime);
                System.out.println("Event Code: " + eventCode);

                // Delete the schedule from the database
                String deleteSql = "DELETE FROM schedule_plans WHERE user_email = ? AND schedule_date = ? AND start_time = ? AND end_time = ? AND event_code = ?";
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagementsystemdatabase", "root", "");
                     PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                    deletePstmt.setString(1, userEmail);
                    deletePstmt.setDate(2, sqlDate);
                    deletePstmt.setTime(3, sqlStartTime);
                    deletePstmt.setTime(4, sqlEndTime);
                    deletePstmt.setString(5, eventCode);
                    deletePstmt.executeUpdate();

                    // Removes row from table
                    tableModel.removeRow(row);

                    JOptionPane.showMessageDialog(null, "Event removed from schedule successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to remove the event.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        if (tableModel.getRowCount() > 0) {

            super.fireEditingStopped();
    }}
}
