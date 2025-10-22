package com.example.goos;

import javax.swing.table.AbstractTableModel;

import static com.example.goos.Main.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel{
    private String statusText = STATUS_JOINING;

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }
}
