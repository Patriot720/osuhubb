package example.cerki.osuhub;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by cerki on 02-Dec-17.
 */

public class JobService extends android.app.job.JobService {
    int mNotificationId = 1;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        // TODO JOB
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentText("Wat");
        builder.setContentTitle("Wut");
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(mNotificationId++,notification);
        }
        Util.scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
