package example.cerki.osuhub.API;

import java.util.List;

import example.cerki.osuhub.API.POJO.Beatmap;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.API.POJO.User;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cerki on 10-Dec-17.
 */

public interface OsuApiService {
    @GET("get_beatmaps")
    Single<List<Beatmap>> getBeatmapBy(@Query("b")String id);
    @GET("get_user")
    Single<List<User>>  getUserBy(@Query("u")String idOrUsername);
    @GET("get_user_best")
    Single<List<BestScore>> getBestScoresBy(@Query("u")String idOrUsername);
}
