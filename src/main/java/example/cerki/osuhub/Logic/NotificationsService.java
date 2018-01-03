package example.cerki.osuhub.Logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.TaskParams;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.POJO.Beatmap;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.Following;
import example.cerki.osuhub.Data.POJO.FeedItem;
import example.cerki.osuhub.Data.Factories.FeedItemFactory;
import example.cerki.osuhub.Data.POJO.Score;
import example.cerki.osuhub.R;

public class NotificationsService extends GcmTaskService {
    private static final String PERIODIC_SYNC_TAG = "tag";
    private static final long SYNC_PERIOD_SECONDS = 1800; // TODO extract to settings
    private static int mNotificationId = 1;
    private ApiDatabase db;

    @Override
    public int onRunTask(TaskParams taskParams) {
        db = ApiDatabase.createInstance(this);
        notifyForEachFollowing();
        return GcmNetworkManager.RESULT_SUCCESS;
    }
    public void notifyForEachFollowing() {
        Collection<Following> all = db.followingDao().getAll();
        for (Following f : all) {
            OsuAPI.getApi().getBestScoresBy(f.id).subscribe((scores)->this.pushNotificationsFor(f,scores),
                    Throwable::printStackTrace);
        }
    }

    public void pushNotificationsFor(Following f,List<BestScore> scores){
        for (BestScore score : scores)
            if(score.isNewerThan(f.realTimestamp)) {
                Beatmap beatmap = OsuAPI.getBeatmapBy(score.getBeatmapId());
                pushNotification(f.username, score, beatmap);
            }
        f.realTimestamp = new Date().getTime();
        db.followingDao().insert(f);
    }

    public void pushNotification(String username,BestScore score,Beatmap beatmap){
        FeedItem feeditem = FeedItemFactory.getFeeditem(username, score, beatmap); // Todo fix consecutive notifications
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(username);
        builder.setContentText(feeditem.toString());
        builder.setAutoCancel(true);
        Notification.BigTextStyle bigNotification = new Notification.BigTextStyle(builder);
        bigNotification.bigText(feeditem.toString()); // Todo add intent player page
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(mNotificationId++, bigNotification.build());
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
