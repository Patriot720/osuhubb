package example.cerki.osuhub.ui.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.FeedItem;
import example.cerki.osuhub.Logic.Tasks.RecentScoresTask;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class PlayerTopPlaysFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,
        FlexibleAdapterExtension.OnDataUpdatedListener,
FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.EndlessScrollListener{
    public static final String ARG_USER_ID = "userId";
    public static final String ARG_RAW_SCORES = "items";
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private FlexibleAdapterExtension adapter;
    private int userId;
    private List<BestScore> rawScores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list,container,false);
        ButterKnife.bind(view);
        adapter = new FlexibleAdapterExtension(null,this);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        initDataDatabase();
        return view;
    }

    protected void onItemClick(FeedItem item) {
        Intent intent = new Intent(getActivity(), BeatmapActivity.class);
        intent.putExtra("beatmap_id",item.beatmap_id);
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
        getArguments().putSerializable(ARG_RAW_SCORES, (Serializable) rawScores);
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
                    new RecentScoresTask(adapter::updateDataSet, items).execute(0);
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
        new RecentScoresTask(adapter::onLoadMoreComplete, rawScores).execute(lastPosition);
    }

    @Override
    public void onRefresh() {
        initDataDatabase();
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = adapter.getItem(position);
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
