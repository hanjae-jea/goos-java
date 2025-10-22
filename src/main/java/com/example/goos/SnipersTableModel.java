package com.example.goos;

import javax.swing.table.AbstractTableModel;

import com.example.goos.SniperSnapshot.SniperState;

public class SnipersTableModel extends AbstractTableModel {
    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private SniperSnapshot snapshot = STARTING_UP;

    private final static String[] STATUS_TEXT = {
        "joining",
        "bidding",
        "winning",
        "lost",
        "won",
    };

    public enum Column {
        ITEM_IDENTIFIER("Item") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.itemId;
            }
        },
        LAST_PRICE("Last Price") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.lastPrice;
            }
        },
        LAST_BID("Last Bid") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return snapshot.lastBid;
            }
        },
        SNIPER_STATE("State") {
            @Override
            public Object valueIn(SniperSnapshot snapshot) {
                return SnipersTableModel.textFor(snapshot.state);
            }
        };

        public final String name;
        public static Column at(int offset) { return values()[offset]; }

        private Column(String name) {
            this.name = name;
        }

        abstract public Object valueIn(SniperSnapshot snapshot);
    }

    

    @Override
    public String getColumnName(int column) {
        return Column.at(column).name;
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
        return Column.at(columnIndex).valueIn(snapshot);
    }

    public void sniperStatusChanged(SniperSnapshot snapshot) {
        this.snapshot = snapshot;

        fireTableRowsUpdated(0, 0);
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }
}
