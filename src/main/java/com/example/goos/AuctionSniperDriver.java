package com.example.goos;

import static org.hamcrest.Matchers.equalTo;

import static com.example.goos.Main.MainWindow.SNIPER_STATUS_NAME;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;

import static java.lang.String.valueOf;

import javax.swing.table.JTableHeader;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import static com.objogate.wl.swing.matcher.IterableComponentsMatcher.matching;

public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeoutMills) {
        super(new GesturePerformer(), JFrameDriver.topLevelFrame(named(Main.MAIN_WINDOW_NAME), showingOnScreen()),
                new AWTEventQueueProber(timeoutMills, 100));
    }

    /** 
     * statusText(joining, lost...) 가 있는지만 확인해줌 (얼마인지는 구체적으로 확인 안함)
     * */ 
    public void showSniperStatus(String statusText) {
        new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
    }


    /**
     * ---------------------------------------------
     * | itemId | lastPrice | lastBid | statusText |
     * ---------------------------------------------
     * 가 정확하게 존재하는지 확인하는 메소드
     */
    public void showSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
        JTableDriver table = new JTableDriver(this);
        table.hasRow(matching(
            withLabelText(itemId), withLabelText(valueOf(lastPrice)), withLabelText(valueOf(lastBid)), withLabelText(statusText))
        );
    }

    public void hasColumnTitles() {
        JTableHeaderDriver headers = new JTableHeaderDriver(this, JTableHeader.class);
        headers.hasHeaders(matching(withLabelText("Item"), withLabelText("Last Price"), withLabelText("Last Bid"), withLabelText("State")));
    }
}
