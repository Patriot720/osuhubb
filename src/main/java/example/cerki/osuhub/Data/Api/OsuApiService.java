package example.cerki.osuhub.Data.Api;

import java.util.List;

import example.cerki.osuhub.Data.POJO.Beatmap;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.Score;
import example.cerki.osuhub.Data.POJO.User;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cerki on 10-Dec-17.
 */

public interface OsuApiService {
    @GET("get_beatmaps")
    Single<List<Beatmap>> getBeatmapBy(@Query("b")String id);
    @GET("get_beatmaps")
    Single<List<Beatmap>> getBeatmapBy(@Query("b") int id);
    @GET("get_user")
    Single<List<User>>  getUserBy(@Query("u")String idOrUsername);
    @GET("get_user_best?limit=50")
    Single<List<BestScore>> getBestScoresBy(@Query("u")String idOrUsername);
    @GET("get_user_best?limit=50")
    Single<List<BestScore>> getBestScoresBy(@Query("u") int user_id); // Todo remove rxJava maybe
    @GET("get_scores")
    Single<List<Score>> getScoresBy(@Query("b") int beatmap_id);
    @GET("get_user")
    Single<List<User>> getUserBy(@Query("u")int mUserId);
    @GET("get_scores")
    Single<List<Score>> getScoresBy(@Query("b") int beatmap_id,@Query("mods") int modFlags);
    @GET("get_scores")
    Single<List<Score>> getScoresBy(@Query("b") int beatmap_id,@Query("u") String usernameOrId);
    @GET("get_user_recent?limit=100")
    Single<List<BestScore>> getRecentScoresBy(@Query("u") String usernameOrId);
    @GET("get_user_recent?limit=100")
    Single<List<BestScore>> getRecentScoresBy(@Query("u") int userId);
    @GET("get_user_recent")
    Single<List<BestScore>> getRecentScoresBy(@Query("u") int userId,@Query("limit") int limit);
}
