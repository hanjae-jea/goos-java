package com.example.goos;

public interface SniperListener {
    void sniperLost();

    void sniperStateChanged(SniperSnapshot sniperState);

    void sniperWon();
}
