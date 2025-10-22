package com.example.goos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.objogate.exception.Defect;

public class SniperSnapshot {
    public enum SniperState {
        JOINING {
            @Override
            public SniperState whenAuctionClosed() {
                return LOST;
            }
        },
        BIDDING {
            @Override
            public SniperState whenAuctionClosed() {
                return LOST;
            }
        },
        WINNING {
            @Override
            public SniperState whenAuctionClosed() {
                return WON;
            }
        },
        LOST,
        WON;

        public SniperState whenAuctionClosed() {
            throw new Defect("Auction is already closed");
        }
    }

    public final String itemId;
    public final int lastPrice;
    public final int lastBid;
    public SniperState state;

    public SniperSnapshot(String itemId, int lastPrice, int lastBid, SniperState state) {
        this.itemId = itemId;
        this.lastPrice = lastPrice;
        this.lastBid = lastBid;
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static SniperSnapshot joining(String itemId) {
        return new SniperSnapshot(itemId, 0, 0, SniperState.JOINING);
    }

    public SniperSnapshot winning(int price) {
        return new SniperSnapshot(itemId, price, price, SniperState.WINNING);
    }

    public SniperSnapshot bidding(int price, int bid) {
        return new SniperSnapshot(itemId, price, bid, SniperState.BIDDING);
    }

    public SniperSnapshot closed() {
        return new SniperSnapshot(itemId, lastPrice, lastBid, state.whenAuctionClosed());
    }
}
