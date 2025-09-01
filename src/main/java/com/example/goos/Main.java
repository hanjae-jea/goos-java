package com.example.goos;

import java.awt.Color;
import static java.lang.String.format;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import static com.example.goos.Main.MainWindow.SNIPER_STATUS_NAME;

public class Main {
    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";

    @SuppressWarnings("unused") private Chat notTobeGCd;


    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    private static JLabel createLabel(String initialText) {
        JLabel result = new JLabel(initialText);
        result.setName(SNIPER_STATUS_NAME);
        result.setBorder(new LineBorder(Color.BLACK));
        return result;
    }

    private static XMPPConnection connection(String hostName, String userName, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostName);
        connection.connect();
        connection.login(userName, password, AUCTION_RESOURCE);
        return connection;
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.joinAuction(connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
    }

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), new MessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                SwingUtilities.invokeLater(() -> {
                    ui.showStatus(MainWindow.STATUS_LOST); 
                });
            }
        });
        this.notTobeGCd = chat;

        chat.sendMessage(new Message());
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            ui = new MainWindow(); 
        });
    }



    public class MainWindow extends JFrame{
        public static final String STATUS_JOINING = "joining";
        public static final String STATUS_LOST = "lost";

        public static final String SNIPER_STATUS_NAME = "sniper status";
        private final JLabel sniperStatus = createLabel(STATUS_JOINING);

        public MainWindow() {
            super("Auction Sniper");
            setName(MAIN_WINDOW_NAME);
            add(sniperStatus);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        public void showStatus(String status) {
            sniperStatus.setText(status);
        }
    }
}