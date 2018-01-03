package example.cerki.osuhub.ui.Fragments;

import android.content.Intent;

import java.util.List;

import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;
import example.cerki.osuhub.Data.POJO.FeedItem;

/**
 * Created by cerki on 27.12.2017.
 */

public abstract class FeedRecyclerFragment extends GenericRecyclerFragment<FeedItem> {
    List<BestScore> rawScores;

    public List<BestScore> getRawScores(){
        return rawScores;
    }

    public void setRawScores(List<BestScore> rawScores) {
        this.rawScores = rawScores;
    }

    @Override
    protected int getEndlessScrollPageSize() {
        return 10;
    }

    @Override
    protected void onItemClick(FeedItem item) {
        Intent intent = new Intent(getActivity(), BeatmapActivity.class);
        intent.putExtra("beatmapId",item.beatmap_id);
        startActivity(intent);
    }
}
