package example.cerki.osuhub.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.util.Date;
import java.util.List;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.ApiDatabase.UserDao;
import example.cerki.osuhub.API.POJO.Beatmap;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.Util;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cerki on 10-Dec-17.
 */

public class OsuAPI {
    public static OsuApiService getApi() {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("k", Util.API_KEY)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> Util.parsePeppyTime(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                .create();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return build.create(OsuApiService.class);
    }

    public static Beatmap getBeatmapBy(int id) {
        ApiDatabase db = ApiDatabase.getInstance();
        Beatmap dbBeatmap = db.beatmapDao().getBy(id);
        if (dbBeatmap != null)
            return dbBeatmap;
        List<Beatmap> beatmaps = getApi().getBeatmapBy(id).blockingGet();
        if (beatmaps.size() > 0) {
            Beatmap beatmap = beatmaps.get(0);
            db.beatmapDao().insert(beatmap);
            return beatmap;
        }
        return new Beatmap();
    }
}
