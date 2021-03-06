package example.cerki.osuhub.PlayerFragment.Overview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;



class AvatarTask extends AsyncTask<String,Void,Bitmap>{
    AvatarTask(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }
    interface WorkDoneListener{
        void workDone(Bitmap bitmap);
    }

    private WorkDoneListener workDoneListener;
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        workDoneListener.workDone(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        InputStream inputStream = null;
        try {
            Document page = Jsoup.connect(url[0]).get();
            String src = getAvatarUrl(page);
            inputStream = new URL(src).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }
    String getAvatarUrl(Document page) {
        return page.select(".usercard__avatar").attr("src");
    }
}
