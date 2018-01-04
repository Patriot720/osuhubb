package example.cerki.osuhub.ui.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.POJO.BestScore;
import example.cerki.osuhub.Logic.Tasks.RecentScoresTask;
import example.cerki.osuhub.R;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class PlayerRecentScoresFragment extends Fragment {

    public void initDataDatabase() {
        OsuAPI.getApi().getRecentScoresBy(getUserId())
                .subscribeOn(Schedulers.newThread())
                .subscribe(items -> {new RecentScoresTask(this::onUpdate,items).execute(0);setRawScores(items);});
    }

    @Override
    protected void onFabButtonClick(View view) {
            removeFailedScores();
            new RecentScoresTask(this::onUpdate,getRawScores()).execute(0);
    }



    public void removeFailedScores() {
        List<BestScore> items = getRawScores();
        List<BestScore> newItems = new ArrayList<>();
        for (BestScore item : items)
            if(!item.getRank().equals("F"))
                newItems.add(item);
        setRawScores(newItems);
    }

    @Override
    public void updateData() {
        initDataDatabase();
    }

    public static PlayerRecentScoresFragment newInstance(int userId, String username){
        PlayerRecentScoresFragment recentScoresFragment = new PlayerRecentScoresFragment();
        recentScoresFragment.setArguments(userId,username);
        return recentScoresFragment;
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        new RecentScoresTask(getAdapter()::onLoadMoreComplete,getRawScores());
    }
}
