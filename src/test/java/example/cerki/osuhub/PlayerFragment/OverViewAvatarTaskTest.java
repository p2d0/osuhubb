package example.cerki.osuhub.PlayerFragment;

import android.graphics.Bitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;

/**
 * Created by cerki on 02-Dec-17.
 */
public class OverViewAvatarTaskTest {

    private Document mDocument;

    @Test
    public void getAvatar() throws Exception {
        String url = new OverViewAvatarTask(new OverViewAvatarTask.WorkDoneListener() {
            @Override
            public void workDone(Bitmap bitmap) {

            }
        }).getAvatarUrl(mDocument);
        assertEquals("./card_files/2558286",url);
    }

    @Before
    public void setUp() throws Exception {
        String path = new File("").getAbsolutePath();
        File f = new File(path + "/app/src/test/resources/card.html");
        mDocument = Jsoup.parse(f,"utf8");
    }
}