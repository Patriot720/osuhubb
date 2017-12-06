package example.cerki.osuhub.Feed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by cerki on 05-Dec-17.
 */

public class BeatmapImageTask extends AsyncTask<String,Void,Bitmap>{
    WorkDoneListener workDoneListener;
    interface WorkDoneListener{
        void workDone(Bitmap bitmap);
    }

    public BeatmapImageTask(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        workDoneListener.workDone(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            return BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
