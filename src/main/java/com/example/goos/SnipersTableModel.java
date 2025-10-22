package com.example.goos;

import javax.swing.table.AbstractTableModel;

import com.example.goos.SniperSnapshot.SniperState;
import static com.example.goos.Main.MainWindow.*;

public class SnipersTableModel extends AbstractTableModel {
    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private String statusText = STATUS_JOINING;
    private SniperSnapshot snapshot = STARTING_UP;

    private static String[] STATUS_TEXT = {
        STATUS_JOINING,
        STATUS_BIDDING,
        STATUS_WINNING,
        STATUS_LOST,
        STATUS_WON,
    };

    public enum Column {
        ITEM_IDENTIFIER,
        LAST_PRICE,
        LAST_BID,
        SNIPER_STATE;

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
                return snapshot.itemId;
            case LAST_PRICE:
                return snapshot.lastPrice;
            case LAST_BID:
                return snapshot.lastBid;
            case SNIPER_STATE:
                return statusText;
            default:
                throw new IllegalArgumentException("Column Index Error " + columnIndex);
        }
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
        fireTableRowsUpdated(0, 0);
    }

    public void sniperStatusChanged(SniperSnapshot snapshot) {
        this.snapshot = snapshot;
        this.statusText = STATUS_TEXT[snapshot.state.ordinal()];

        fireTableRowsUpdated(0, 0);
    }
}
