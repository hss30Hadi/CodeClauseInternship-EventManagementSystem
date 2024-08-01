import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(Color.black);
            setBackground(new Color(246, 62, 64));
        } else {
            setForeground(Color.black);
            setBackground(new Color(246, 62, 64));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

