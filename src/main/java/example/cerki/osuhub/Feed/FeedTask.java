package example.cerki.osuhub.Feed;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Collection;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.Notifications.Following;
import example.cerki.osuhub.OsuDb;

/**
 * Created by cerki on 05-Dec-17.
 */

public class FeedTask extends AsyncTask<Void,Void,Collection<FeedItem>> {
    private final Context mContext;

    interface WorkDoneListener{
        public void workDone();
    }
    WorkDoneListener workDoneListener;


    public FeedTask(Context context, WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
        this.mContext = context;
    }

    @Override
    protected Collection<FeedItem> doInBackground(Void... voids) {
        Collection<Following> all = new FollowersTable(new OsuDb(mContext).getWritableDatabase()).getAll();
        for (Following following : all) {
        }

        return null;
    }
}
