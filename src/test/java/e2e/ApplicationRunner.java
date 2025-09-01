package e2e;

import javax.swing.SwingUtilities;

import com.example.goos.AuctionSniperDriver;
import com.example.goos.Main;
import static com.example.goos.Main.MainWindow.STATUS_JOINING;
import static com.example.goos.Main.MainWindow.STATUS_LOST;

import static e2e.FakeAuctionServer.XMPP_HOSTNAME;

public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    
    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
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
        driver.showSniperStatus(STATUS_JOINING);
    }

    public void showsSniperHasLostAuction() {
        driver.showSniperStatus(STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }

    private void makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock() {
    try {
      SwingUtilities.invokeAndWait(() -> {});
    } catch (Exception e) {
      throw new AssertionError(e);
    }
  }
}
