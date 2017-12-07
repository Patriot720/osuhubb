package example.cerki.osuhub;

import android.content.ContentValues;

import example.cerki.osuhub.Feed.Beatmap;
import example.cerki.osuhub.List.Player;

public class ContentValuesFactory {
    public static ContentValues generate(Player p) {
        ContentValues cv = new ContentValues();
        cv.put(Columns.ID, p.getId());
        cv.put(Columns.USERNAME, p.getUsername());
        cv.put(Columns.COUNTRY, p.getCountry());
        cv.put(Columns.ACTIVITY, p.getActivity());
        for (String key : p.getKeySet()) {
            String val = p.getString(key);
            if (val.contains("."))
                cv.put(key, Float.parseFloat(val));
            else
                cv.put(key, Integer.parseInt(val));
        }
        return cv;
    }
    public static ContentValues generate(Beatmap beatmap){
        ContentValues cv = new ContentValues();
        for(String key : beatmap.keySet()){
            cv.put(key,beatmap.get(key));
        }
        return cv;
    }
}