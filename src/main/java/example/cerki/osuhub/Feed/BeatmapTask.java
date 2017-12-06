package example.cerki.osuhub.Feed;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

import example.cerki.osuhub.OsuAPI;

/**
 * Created by cerki on 05-Dec-17.
 */
// TODO extract interface
class BeatmapTask extends AsyncTask<String,Void,Beatmap>{
    interface WorkDoneListener{
        public void workDone(Beatmap beatmap);
    }

    public BeatmapTask(WorkDoneListener workDoneListener) {
        this.workDoneListener = workDoneListener;
    }

    public BeatmapTask() {
    }

    private WorkDoneListener workDoneListener;
    @Override
    protected Beatmap doInBackground(String... strings) {
        try {
            return new OsuAPI().getBeatmap(strings[0]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Beatmap beatmap) {
        workDoneListener.workDone(beatmap);
    }
}
