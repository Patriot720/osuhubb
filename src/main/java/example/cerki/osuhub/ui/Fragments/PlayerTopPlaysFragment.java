package example.cerki.osuhub.ui.Fragments;

import android.content.Intent;

import java.util.Collections;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;
import example.cerki.osuhub.API.POJO.FeedItem;
import example.cerki.osuhub.PlayerFragment.RecentPlays.RecentScoresTask;
import example.cerki.osuhub.R;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class PlayerTopPlaysFragment extends FeedRecyclerFragment{

    @Override
    protected int getEndlessScrollPageSize() {
        return 10;
    }

    @Override
    protected void onItemClick(FeedItem item) {
        Intent intent = new Intent(getActivity(), BeatmapActivity.class);
        intent.putExtra("beatmap_id",item.beatmap_id);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }


    public static PlayerTopPlaysFragment newInstance(int userId) {
        PlayerTopPlaysFragment fragment = new PlayerTopPlaysFragment();
        fragment.setArguments(userId);
        return fragment;
    }
    @Override
    public void initDataDatabase() {
        OsuAPI.getApi().getBestScoresBy(getUserId())
                .subscribeOn(Schedulers.newThread())
                .subscribe(items -> {
                    new RecentScoresTask(this::onUpdate, items).execute(0);
                    setRawScores(items);
                });
    }
    public void sortItemsByDate(){
        Collections.sort(getRawScores(), (bestScore, t1) -> bestScore.getDate().compareTo(t1.getDate()));
    }

    @Override
    public void updateData() {
        initDataDatabase();
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        new RecentScoresTask(getAdapter()::onLoadMoreComplete, getRawScores()).execute(lastPosition);
    }
}
