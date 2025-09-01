package com.example.goos;

import static org.hamcrest.Matchers.equalTo;

import static com.example.goos.Main.MainWindow.SNIPER_STATUS_NAME;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeoutMills) {
        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()),
                new AWTEventQueueProber(timeoutMills, 100));
    }

    public void showSniperStatus(String statusText) {
        new JLabelDriver(this, named(SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
    }

}
