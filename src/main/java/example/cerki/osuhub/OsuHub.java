package example.cerki.osuhub;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

/**
 * Created by cerki on 14.12.2017.
 */

public class OsuHub extends Application {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(
                    Environment.getExternalStorageDirectory().getPath()));

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}

