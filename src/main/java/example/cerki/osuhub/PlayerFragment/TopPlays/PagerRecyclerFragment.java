package example.cerki.osuhub.PlayerFragment.TopPlays;

import android.os.Bundle;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.GenericRecyclerFragment;
import example.cerki.osuhub.List.ProgressItem;
import example.cerki.osuhub.MainActivity;
import example.cerki.osuhub.PlayerFragment.RecentPlays.RecentScoresFragment;
import example.cerki.osuhub.PlayerFragment.RecentPlays.RecentScoresTask;

/**
 * Created by cerki on 26.12.2017.
 */

@SuppressWarnings("unchecked")
public abstract class PagerRecyclerFragment<T extends IFlexible> extends GenericRecyclerFragment<T> {
    List<BestScore> mItems;

    public List<BestScore> getItems() {
        return mItems;
    }

    public void setItems(List<BestScore> mItems) {
        this.mItems = mItems;
    }

    private String mUsername;

    public PagerRecyclerFragment() {
    }
    @Override
    public void addListeners() {
        FlexibleAdapter adapter = getAdapter();
        adapter.setEndlessScrollListener(new FlexibleAdapter.EndlessScrollListener() {
            @Override
            public void noMoreLoad(int newItemsSize) {

            }

            @Override
            public void onLoadMore(int lastPosition, int currentPage) {
                new RecentScoresTask(adapter::onLoadMoreComplete,mItems).execute(lastPosition);
            }
        },new ProgressItem()).setEndlessPageSize(10);
    }

}
