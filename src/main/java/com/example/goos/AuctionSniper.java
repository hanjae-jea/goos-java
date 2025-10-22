package com.example.goos;

import com.example.goos.SniperSnapshot.SniperState;

public class AuctionSniper implements AuctionEventListener {
    private SniperSnapshot snapshot;

    private final SniperListener sniperListener;
    private final Auction auction;

    public AuctionSniper(Auction auction, String itemId, SniperListener sniperListener) {
        this.auction = auction;
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(itemId);
    }

    @Override
    public void auctionClosed() {
        snapshot = snapshot.closed();
        notifyChange();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource source) {
        switch (source) {
            case FromSniper -> snapshot = snapshot.winning(price);
            case FromOtherBidder -> {
                int bid = price + increment;
                auction.bid(bid);
                snapshot = snapshot.bidding(price, bid);
            }
        }
        notifyChange();
    }
    
    private void notifyChange() {
        sniperListener.sniperStateChanged(snapshot);
    }
}