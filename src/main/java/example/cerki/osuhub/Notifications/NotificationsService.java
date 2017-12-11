package example.cerki.osuhub.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.TaskParams;

import org.json.JSONException;

import java.io.EOFException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.API.POJO.Following;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.PastScores;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Score;

import static android.content.ContentValues.TAG;

public class NotificationsService extends GcmTaskService {
    private static final String PERIODIC_SYNC_TAG = "tag";
    private static final long SYNC_PERIOD_SECONDS = 1800; // TODO extract to settings
    private static int mNotificationId = 1;

    @Override
    public int onRunTask(TaskParams taskParams) {
        FollowersTable followersTable = new FollowersTable(new OsuDb(this).getWritableDatabase());
        Collection<Following> all = followersTable.getAll();
        Collection<Score> newScores;
        try {
            for (Following f : all) {
                newScores = new PastScores(f).getNewScores();
                followersTable.insertOrUpdate(f.id,f.username);
                for (Score score : newScores)
                    pushNotification(score.generateScoreString(f.username));
        }
        followersTable.close();
        } catch(IOException | JSONException | ParseException e){
            if(e instanceof EOFException)
                Log.e(TAG, "Notification: EOFException");
            e.printStackTrace();
        }
        return GcmNetworkManager.RESULT_SUCCESS;
    }


    private void pushNotification(String text) {
        Notification.Builder mBuilder = new Notification.Builder(this);
        // TODO THIS THING ONLY updates notifications if there's more than one
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(getString(R.string.app_name));
        mBuilder.setContentText(text);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert mNotifyMgr != null;
        mNotifyMgr.notify(mNotificationId++, mBuilder.build());
    }

    public static void scheduleSync(Context ctx) {
        GcmNetworkManager gcmNetworkManager = GcmNetworkManager.getInstance(ctx);
        PeriodicTask periodicTask = new PeriodicTask.Builder()
                .setPeriod(SYNC_PERIOD_SECONDS) // occurs at *most* once this many seconds - note that you can't control when
                .setRequiredNetwork(PeriodicTask.NETWORK_STATE_CONNECTED) // various connectivity scenarios are available
                .setTag(PERIODIC_SYNC_TAG) // returned at execution time to your endpoint
                .setService(NotificationsService.class) // the GcmTaskServer you created earlier
                .setPersisted(true) // persists across reboots or not
                .setUpdateCurrent(true) // replace an existing task with a matching tag - defaults to false!
                .build();
        gcmNetworkManager.schedule(periodicTask);
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        NotificationsService.scheduleSync(this);
    }
}
