package com.example.goos;

public class AuctionSniper implements AuctionEventListener {
    private boolean isWinning = false;
    private final SniperListener sniperListener;
    private final Auction auction;
    private String itemId = "ITEM_ID";
    
    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.auction = auction;
        this.sniperListener = sniperListener;
    }

    public void auctionClosed() {
        if (isWinning) {
            sniperListener.sniperWon();
        } else {
            sniperListener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource source) {
        isWinning = source == PriceSource.FromSniper;
        if (isWinning) {
            sniperListener.sniperWinning();
        } else {
            int bid = price + increment;
            auction.bid(bid);
            sniperListener.sniperBidding(new SniperState(itemId, price, bid));
        }

    }
}
