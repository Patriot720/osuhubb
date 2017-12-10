package example.cerki.osuhub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Util {
    public static final String BASE_URL = "https://osu.ppy.sh/api/";
    public static final String API_KEY = "b40b7a7a8207b1ebd870eaf1f74bd2995f1a2cb6";
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String makeRequestUrl(String method,String query){
        return BASE_URL + method + "?k=" + API_KEY + "&" + query;
    }

    @SuppressLint("DefaultLocale")
    public static String getAccuracyString(Score score){
        float count50 = score.getAsInt("count50");
        float count100 = score.getAsInt("count100");
        float count300 = score.getAsInt("count300");
        float countmiss = score.getAsInt("countmiss");
        float acc = ((50*count50) + (100*count100) + (300*count300))/(300*(countmiss + count50 + count100 + count300));
        return String.format("%.2f%%",acc*100);
    } // TODO change not a clear method name
    public static Date parseTimestamp(String stamp,TimeZone timezone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        format.setTimeZone(timezone);
        return format.parse(stamp);
    }

    public static Date parseTimestamp(String stamp) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN, Locale.getDefault());
        return format.parse(stamp);
    }
    public static Date parsePeppyTime(String stamp) throws ParseException{
        return parseTimestamp(stamp,TimeZone.getTimeZone("GMT+8"));
    }
    public static Date parseSQLTime(String stamp) throws ParseException {
        return parseTimestamp(stamp,TimeZone.getTimeZone("GMT"));
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
   public static void setImageFromAsset(Context context, ImageView destination, String source) {
        try {
            InputStream open = context.getAssets().open(source);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            destination.setImageBitmap(bitmap);
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }
    }
    public static String doubleToString(Double value){
        if(value % 1 == 0) {
            int val = value.intValue();
            return String.valueOf(val);
        }
        return String.format(Locale.getDefault(),"%.2f",value);
    }

}
