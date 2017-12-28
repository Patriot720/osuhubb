package example.cerki.osuhub.PlayerFragment.RecentPlays;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.FeedRecyclerFragment;
import example.cerki.osuhub.R;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class RecentScoresFragment extends FeedRecyclerFragment{

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }

    @Override
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

    public static RecentScoresFragment newInstance(int userId,String username){
        RecentScoresFragment recentScoresFragment = new RecentScoresFragment();
        recentScoresFragment.setArguments(userId,username);
        return recentScoresFragment;
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        new RecentScoresTask(getAdapter()::onLoadMoreComplete,getRawScores());
    }
}
