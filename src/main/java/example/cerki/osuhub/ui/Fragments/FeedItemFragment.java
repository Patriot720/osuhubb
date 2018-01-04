package example.cerki.osuhub.ui.Fragments;

import example.cerki.osuhub.Logic.Tasks.FeedDbTask;
import example.cerki.osuhub.Logic.Tasks.FeedNetworkTask;
import example.cerki.osuhub.R;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class FeedItemFragment extends FeedRecyclerFragment{
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }


    @Override
    public void initDataDatabase() {
        new FeedDbTask(items -> {
            this.onUpdate(items);
            updateData();
        }).execute();
    }

    @Override
    public void updateData() {
        setUpdating(true);
        Single.fromCallable(FeedNetworkTask::getFeedItems) // Todo refactor
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItemsOnTop);
    }

    public static FeedItemFragment newInstance(){
        return new FeedItemFragment();
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {

    }
}
