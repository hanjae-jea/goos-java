package com.example.goos;

public interface SniperListener {
    void sniperLost();

    void sniperBidding(SniperState sniperState);

    void sniperWinning();

    void sniperWon();
}
