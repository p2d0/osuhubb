package example.cerki.osuhub;

import android.database.sqlite.SQLiteDatabase;


import org.junit.Before;
import org.junit.Test;

import example.cerki.osuhub.List.Player;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

/**
 * Created by cerki on 30-Nov-17.
 */
public class PlayersTableTest {

    private PlayersTable playersTable;

    @Before
    public void setUp() throws Exception {
        SQLiteDatabase writableDatabase = new OsuDb(getTargetContext()).getWritableDatabase();
        playersTable = new PlayersTable(writableDatabase);
    }

    @Test
    public void insertion() throws Exception {
        Player p = TestHelper.getFakePlayer();
        playersTable.insertPlayer(p);
        Player player = playersTable.getPlayer(p.getId());
        TestHelper.assertPlayer(player);
    }
}