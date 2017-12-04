package example.cerki.osuhub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import example.cerki.osuhub.List.Player;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Util {
    @SuppressLint("DefaultLocale")
    public static String calculateAccuracy(Score score){
        float count50 = score.getAsInt("count50");
        float count100 = score.getAsInt("count100");
        float count300 = score.getAsInt("count300");
        float countmiss = score.getAsInt("countmiss");
        float acc = ((50*count50) + (100*count100) + (300*count300))/(300*(countmiss + count50 + count100 + count300));
        return String.format("%.2f%%",acc*100);
    }
    public static Date parseTimestamp(String stamp,TimeZone timezone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(timezone);
        return format.parse(stamp);
    }

    public static Date parseTimestamp(String stamp) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.parse(stamp);
    }

    @SuppressLint("SetTextI18n")
    public static void showPlayerDifference(TextView textView, ImageView arrow, Double value) {
        if(value == null) {
            arrow.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            return;
        }
        if(value < 0.01 && value > -0.01){
            textView.setVisibility(View.INVISIBLE);
            arrow.setVisibility(View.INVISIBLE);
            return;
        }
        if(value < 0){
            arrow.setImageResource(R.drawable.arrow_down);
            value = value * -1;
        }
        textView.setText(doubleToString(value));
        arrow.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
    }

    public static String doubleToString(Double value){

        if(value % 1 == 0) {
            int val = value.intValue();
            return String.valueOf(val);
        }
        return String.format(Locale.getDefault(),"%.2f",value);
    }

    public static ContentValues generateContentValues(Player p) {
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
}
