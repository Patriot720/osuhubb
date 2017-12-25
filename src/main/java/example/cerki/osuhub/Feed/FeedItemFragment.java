package example.cerki.osuhub.Feed;

import android.support.v4.widget.SwipeRefreshLayout;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import example.cerki.osuhub.GenericRecyclerFragment;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 24.12.2017.
 */

@SuppressWarnings("unchecked")
public class FeedItemFragment extends GenericRecyclerFragment<FeedItem> {
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }

    @Override
    public void addListeners() {
        FlexibleAdapter<FeedItem> adapter = getAdapter();
        adapter.addListener((FlexibleAdapter.OnItemClickListener) position -> {
            FeedItem item = adapter.getItem(position);
            getListener().onListFragmentInteraction(item);
            return true;
        });
    }

    @Override
    public void initData() {
        new NewFeedTaskDb(items -> {
            getAdapter().updateDataSet(items);
            getRefresher().setRefreshing(false);
            if(Util.isNetworkAvailable(getContext()))
                updateData();
        }).execute();
    }

    @Override
    public void updateData() {
        SwipeRefreshLayout refresher = getRefresher();
        refresher.setRefreshing(true);
        new NewFeedTaskNetwork(items -> {
            getAdapter().addItems(0,items);
            refresher.setRefreshing(false);
        }).execute();
    }
}
