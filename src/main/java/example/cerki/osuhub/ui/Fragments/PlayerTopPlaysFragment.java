package example.cerki.osuhub.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.FeedItem;
import example.cerki.osuhub.Logic.Tasks.RecentScoresTask;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class PlayerTopPlaysFragment extends GenericRecyclerFragment implements SwipeRefreshLayout.OnRefreshListener ,
FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.EndlessScrollListener{
    public static final String ARG_USER_ID = "userId";
    public static final String ARG_RAW_SCORES = "items";
    private int userId;
    private List<BestScore> rawScores;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initDataDatabase();
    }

    protected void onItemClick(FeedItem item) {
        Intent intent = new Intent(getActivity(), BeatmapActivity.class);
        intent.putExtra("beatmapId" , item.beatmap_id);
        startActivity(intent);
    }

    public static PlayerTopPlaysFragment newInstance(int userId) {

        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID,userId);

        PlayerTopPlaysFragment fragment = new PlayerTopPlaysFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bundle arguments = getArguments();
        if(arguments != null)
            arguments.putSerializable(ARG_RAW_SCORES, (Serializable) rawScores);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            userId = arguments.getInt(ARG_USER_ID);
            rawScores = (List<BestScore>) arguments.getSerializable(ARG_RAW_SCORES);
        }

    }

    public void initDataDatabase() {
        OsuAPI.getApi().getBestScoresBy(userId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(items -> {
                    new RecentScoresTask(getAdapter()::updateDataSet, items).execute(0);
                    rawScores = items;
                });
    }

    public void sortItemsByDate(){
        Collections.sort(rawScores, (bestScore, t1) -> bestScore.getDate().compareTo(t1.getDate()));
    }

    @Override
    public void noMoreLoad(int newItemsSize) {
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        new RecentScoresTask(getAdapter()::onLoadMoreComplete, rawScores).execute(lastPosition);
    }

    @Override
    public void onRefresh() {
        initDataDatabase();
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = getAdapter().getItem(position); // Todo refactor to one class
        if(item instanceof FeedItem){
            FeedItem feedItem = (FeedItem) item;
            onItemClick(feedItem);
        }
        return true;
    }

    @Override
    public void onDataUpdated() {
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

}
