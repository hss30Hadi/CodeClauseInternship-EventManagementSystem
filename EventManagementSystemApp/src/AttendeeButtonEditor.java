import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;

class AttendeeButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private String userEmail;
    private DefaultTableModel tableModel;
    private JTable table;

    public AttendeeButtonEditor(JCheckBox checkBox, String userEmail, DefaultTableModel tableModel, JTable table) {
        super(checkBox);
        this.userEmail = userEmail;
        this.tableModel = tableModel;
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
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
                String name = tableModel.getValueAt(row, 0).toString();
                String email = tableModel.getValueAt(row, 1).toString();
                String eventCode = tableModel.getValueAt(row, 2).toString();

                // Log values for debugging
                System.out.println("Removing attendee:");
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Event Code: " + eventCode);

                // Delete the attendee from the database
                String deleteSql = "DELETE FROM attendees WHERE email = ? AND event_code = ?";
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagementsystemdatabase", "root", "");
                     PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                    deletePstmt.setString(1, email);
                    deletePstmt.setString(2, eventCode);
                    deletePstmt.executeUpdate();

                    // Remove  row from table
                    tableModel.removeRow(row);

                    JOptionPane.showMessageDialog(null, "Attendee removed from event successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to remove the attendee.", "Error", JOptionPane.ERROR_MESSAGE);
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
        super.fireEditingStopped();
    }
}
