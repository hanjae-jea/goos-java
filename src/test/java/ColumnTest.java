import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.example.goos.SniperSnapshot;
import com.example.goos.SnipersTableModel.Column;

public class ColumnTest {
    public static final String ITEM_ID_STRING = "item";

    @Test
    public void simpleTestGetValueAt() {
        assertThat(ITEM_ID_STRING, equalTo(Column.ITEM_IDENTIFIER.valueIn(new SniperSnapshot(ITEM_ID_STRING, 0, 0, SniperSnapshot.SniperState.BIDDING))));
    }
}
