package com.example.goos;

import static org.hamcrest.Matchers.equalTo;

import static com.example.goos.Main.MainWindow.SNIPER_STATUS_NAME;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeoutMills) {
        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()),
                new AWTEventQueueProber(timeoutMills, 100));
    }

    public void showSniperStatus(String statusText) {
        new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
    }
}
