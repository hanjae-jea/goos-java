package e2e;

import javax.swing.SwingUtilities;

import com.example.goos.AuctionSniperDriver;
import com.example.goos.Main;
import com.example.goos.Main.MainWindow;

import static e2e.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_XMPP_ID = "sniper@localhost/Auction";
    public static final String SNIPER_PASSWORD = "sniper";

    private String itemId;

    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        itemId = auction.getItemId();
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock();

        driver = new AuctionSniperDriver(1000);
        driver.showSniperStatus(MainWindow.STATUS_JOINING);
    }

    public void showsSniperHasLostAuction() {
        driver.showSniperStatus(MainWindow.STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }

    private void makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock() {
        try {
            SwingUtilities.invokeAndWait(() -> {
            });
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 
     * @param lastPrice 최종 가격 = 현재 최고 입찰액
     * @param lastBid 다음 입찰액
     */
    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showSniperStatus(itemId, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
    }

    public void hasShownSniperIsWinning(int winningBid) {
        driver.showSniperStatus(itemId, winningBid, winningBid, MainWindow.STATUS_WINNING);
    }

    public void showsSniperHasWonAuction(int lastPrice) {
        driver.showSniperStatus(itemId, lastPrice, lastPrice, MainWindow.STATUS_WINNING);
    }
}
