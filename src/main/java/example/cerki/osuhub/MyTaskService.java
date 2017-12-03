package example.cerki.osuhub;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.TaskParams;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by cerki on 03-Dec-17.
 */

public class MyTaskService extends GcmTaskService {
    private static final String PERIODIC_SYNC_TAG = "tag";
    private static final long SYNC_PERIOD_SECONDS = 50; // TODO extract to settings
    private static int mNotificationId = 1;
    @Override
    public int onRunTask(TaskParams taskParams) {
        FollowersTable followersTable = new FollowersTable(new OsuDb(this).getWritableDatabase());
        Collection<Following> all = followersTable.getAll();
        Collection<Score> newScores = new ArrayList<>();
        for (Following f :
                all) {
            try {
                newScores = FollowersNotificator.getNewScores(f);
                followersTable.insertOrUpdateFollower(f.id);
                for (Score score :
                        newScores) {
                    StringBuilder builder = new StringBuilder();
                    builder
                            .append(f.id)
                            .append(" ")
                            .append(score.get("pp")) // TODO Create ACC CALCULATOR
                            .append(" "); // TODO FETCH MAP DATA
                    // TODO GetUsername from follower, add username to follower
                    // TODO THIS THING ONLY updates notifications if there/s alot
                    pushNotification(builder.toString());
                }
            } catch (IOException | JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
        return GcmNetworkManager.RESULT_SUCCESS;
    }


    private void pushNotification(String text){
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(getString(R.string.app_name));
        mBuilder.setContentText(text);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert mNotifyMgr != null;
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    private void createNotification() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentText("Wat");
        builder.setContentTitle("Wut");
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(mNotificationId++,notification);
        }
    }

    public static void scheduleSync(Context ctx) {
        GcmNetworkManager gcmNetworkManager = GcmNetworkManager.getInstance(ctx);
        PeriodicTask periodicTask = new PeriodicTask.Builder()
                .setPeriod(SYNC_PERIOD_SECONDS) // occurs at *most* once this many seconds - note that you can't control when
                .setRequiredNetwork(PeriodicTask.NETWORK_STATE_CONNECTED) // various connectivity scenarios are available
                .setTag(PERIODIC_SYNC_TAG) // returned at execution time to your endpoint
                .setService(MyTaskService.class) // the GcmTaskServer you created earlier
                .setPersisted(true) // persists across reboots or not
                .setUpdateCurrent(true) // replace an existing task with a matching tag - defaults to false!
                .build();
        gcmNetworkManager.schedule(periodicTask);
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        MyTaskService.scheduleSync(this);
    }
}
