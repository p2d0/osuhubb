package example.cerki.osuhub.Feed;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import example.cerki.osuhub.Util;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by cerki on 09-Dec-17.
 */
@RunWith(RobolectricTestRunner.class) // todo remove all calls to api
public class GsonBeatmapTest {
    @Test
    public void retrofit() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BeatmapService beatmapService = retrofit.create(BeatmapService.class);
        Call<List<GsonBeatmap>> beatmap = beatmapService.getBeatmap(Util.API_KEY, "1262832");
        List<GsonBeatmap> body = beatmap.execute().body();
        assertEquals(body.get(0).getArtist(),"ClariS");
    }
}