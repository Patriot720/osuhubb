package example.cerki.osuhub.ui.Fragments;

import example.cerki.osuhub.Feed.NewFeedTaskDb;
import example.cerki.osuhub.Feed.NewFeedTaskNetwork;
import example.cerki.osuhub.R;


@SuppressWarnings("unchecked")
public class FeedItemFragment extends FeedRecyclerFragment{
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }


    @Override
    public void initDataDatabase() {
        new NewFeedTaskDb(items -> {
            this.onUpdate(items);
            updateData();
        }).execute();
    }

    @Override
    public void updateData() {
        setUpdating(true);
        new NewFeedTaskNetwork(this::addItemsOnTop).execute();
    }
    public static FeedItemFragment newInstance(){
        return new FeedItemFragment();
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {

    }
}