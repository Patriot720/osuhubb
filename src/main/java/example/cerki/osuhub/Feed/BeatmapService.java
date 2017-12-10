package example.cerki.osuhub.Feed;

import java.util.List;

import example.cerki.osuhub.API.POJO.Beatmap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cerki on 09-Dec-17.
 */

public interface BeatmapService {
    @GET("get_beatmaps")
    Call<List<Beatmap>> getBeatmap(@Query("k") String apiKey, @Query("b") String beatmapId);
}
