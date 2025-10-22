package com.example.goos;

import javax.swing.table.AbstractTableModel;

import static com.example.goos.Main.MainWindow.STATUS_JOINING;

public class SnipersTableModel extends AbstractTableModel {
    private final static SniperState STARTING_UP = new SniperState("", 0, 0);
    private String statusText = STATUS_JOINING;
    private SniperState sniperState = STARTING_UP;

    public enum Column {
        ITEM_IDENTIFIER,
        LAST_PRICE,
        LAST_BID,
        SNIPER_STATUS;

        public static Column at(int offset) { return values()[offset]; }
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER:
                return sniperState.itemId;
            case LAST_PRICE:
                return sniperState.lastPrice;
            case LAST_BID:
                return sniperState.lastBid;
            case SNIPER_STATUS:
                return statusText;
            default:
                throw new IllegalArgumentException("Column Index Error " + columnIndex);
        }
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStatusChanged(SniperState sniperState, String statusText) {
        this.sniperState = sniperState;
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }
}
