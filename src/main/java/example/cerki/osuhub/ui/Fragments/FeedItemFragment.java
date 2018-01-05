package example.cerki.osuhub.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.Data.POJO.FeedItem;
import example.cerki.osuhub.Logic.Tasks.FeedDbTask;
import example.cerki.osuhub.Logic.Tasks.FeedNetworkTask;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class FeedItemFragment extends GenericRecyclerFragment
implements
        FlexibleAdapter.OnItemClickListener{
    public FeedItemFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initDataDatabase();
        updateData();
    }

    public void initDataDatabase() {
        new FeedDbTask(items -> getAdapter().updateDataSet(items)).execute();
    }

    public void updateData() {
        refreshLayout.setRefreshing(true);
        Single.fromCallable(FeedNetworkTask::getFeedItems) // Todo refactor
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items->getAdapter().addItems(0,items));
    }

    public static FeedItemFragment newInstance(){
        return new FeedItemFragment();
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = getAdapter().getItem(position);
        if(item instanceof FeedItem) {
            FeedItem feedItem = (FeedItem) item;
            Intent intent = new Intent(getContext(), BeatmapActivity.class);
            intent.putExtra("beatmapId",feedItem.beatmap_id);
            intent.putExtra("coverUrl",feedItem.coverUrl);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        updateData();
    }
}
