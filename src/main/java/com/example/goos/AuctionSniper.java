package com.example.goos;

public class AuctionSniper implements AuctionEventListener {
    private final SniperListener sniperListener;
    private final Auction auction;
    
    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.auction = auction;
        this.sniperListener = sniperListener;
    }

    public void auctionClosed() {
        sniperListener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource source) {
        if (source == PriceSource.FromOtherBidder) {
            auction.bid(price + increment);
            sniperListener.sniperBidding();
        } else {
            sniperListener.sniperWinning();
        }

    }
}
