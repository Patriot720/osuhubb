package example.cerki.osuhub.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Data.POJO.RecentScore;
import example.cerki.osuhub.Logic.Tasks.RecentScoresTask;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class PlayerRecentScoresFragment extends GenericRecyclerFragment
implements FlexibleAdapter.EndlessScrollListener,
        FlexibleAdapter.OnItemClickListener
{
    public static final String ARG_USERNAME = "username";
    public static final String ARG_USER_ID = "userId";
    public static final String ARG_RAW_SCORES = "rawScores";
    private int userId;
    private String username;
    private List<BestScore> rawScores;


    public void initDataDatabase() {
        OsuAPI.getApi().getRecentScoresBy(userId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(items -> {new RecentScoresTask(getAdapter()::updateDataSet,items).execute(0);
                rawScores = items;});
    }

    public void onFabButtonClick() {
            removeFailedScores();
            new RecentScoresTask(getAdapter()::updateDataSet,rawScores).execute(0);
    }



    public void removeFailedScores() {
        List<BestScore> newItems = new ArrayList<>();
        for (BestScore item : rawScores)
            if(!item.getRank().equals("F"))
                newItems.add(item);
        rawScores = newItems;
    }

    public static PlayerRecentScoresFragment newInstance(int userId, String username) {

        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID,userId);
        args.putString(ARG_USERNAME,username);
        PlayerRecentScoresFragment fragment = new PlayerRecentScoresFragment();
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
        if(arguments != null){
            userId = arguments.getInt(ARG_USER_ID);
            username = arguments.getString(ARG_USERNAME);
            rawScores = (List<BestScore>) arguments.getSerializable(ARG_RAW_SCORES);
        }
    }

    @Override
    public void noMoreLoad(int newItemsSize) {
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        new RecentScoresTask(getAdapter()::onLoadMoreComplete,rawScores);
    }

    @Override
    public boolean onItemClick(int position) {
        return false;
    }

    @Override
    public void onRefresh() {
        initDataDatabase();
    }
}
