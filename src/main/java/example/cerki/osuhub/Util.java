package example.cerki.osuhub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import example.cerki.osuhub.API.POJO.BestScore;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Util {
    public static final String BASE_URL = "https://osu.ppy.sh/api/";
    public static final String API_KEY = "b40b7a7a8207b1ebd870eaf1f74bd2995f1a2cb6";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String makeRequestUrl(String method,String query){
        return BASE_URL + method + "?k=" + API_KEY + "&" + query;
    }

    @SuppressLint("DefaultLocale")
    public static String getAccuracyString(BestScore score){
        float count50 = score.getCount50();
        float count100 = score.getCount100();
        float count300 = score.getCount300();
        float countmiss = score.getCountmiss();
        float acc = ((50*count50) + (100*count100) + (300*count300))/(300*(countmiss + count50 + count100 + count300));
        return String.format("%.2f%%",acc*100);
    }

    public static Date parseTimestamp(String stamp) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TIMESTAMP_PATTERN, Locale.getDefault());
        return format.parse(stamp);
    }
    public static Date parseTimestamp(String stamp, TimeZone zone) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(TIMESTAMP_PATTERN,Locale.getDefault());
        format.setTimeZone(zone);
        return format.parse(stamp);
    }
    public static Date parsePeppyTime(String stamp){
        try {
            return parseTimestamp(stamp,TimeZone.getTimeZone("GMT+8"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date parseSQLTime(String stamp) throws ParseException {
        return parseTimestamp(stamp,TimeZone.getTimeZone("GMT"));
    }
    public static Date getMonthOldDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-1);
        return calendar.getTime();
    }

    public static Date getWeekOldDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        return calendar.getTime();
    }

    static public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("LOG", "Error checking internet connection", e);
            }
        } else {
            Log.d("LOG", "No network available!");
        }
        return false;
    }
}
