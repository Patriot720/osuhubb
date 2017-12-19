package example.cerki.osuhub.Searching;

import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class SearchHandler {
    boolean Loading;

    public SearchHandler() {
    }

    public void doMySearch(String query, MaterialSearchView view) {
        // Todo refactor
        Single.fromCallable(() -> {
                String[] result;
                String urlString = "https://osu.ppy.sh/p/profile?check=1&query=" + query;
                URL url = new URL(urlString);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    builder.append(line);
                JSONObject object = new JSONObject(builder.toString());
                JSONArray suggestions = object.getJSONArray("suggestions");
                if (suggestions != null) {
                    result = new String[suggestions.length()];
                    for (int i = 0; i < suggestions.length(); i++)
                        result[i] = suggestions.getString(i);
                    return result;
                }
                return new String[0];
            }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe((items)->{
                        for (String item : items) {
                        Log.d(TAG, "doMySearch: " + item);
                        }
            view.setSuggestions(items);
            view.showSuggestions();
                    });
    }
}