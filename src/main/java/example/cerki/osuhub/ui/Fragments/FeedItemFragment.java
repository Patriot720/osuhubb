package example.cerki.osuhub.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.Data.POJO.FeedItem;
import example.cerki.osuhub.Logic.Tasks.FeedDbTask;
import example.cerki.osuhub.Logic.Tasks.FeedNetworkTask;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class FeedItemFragment extends Fragment
implements FlexibleAdapterExtension.OnDataUpdatedListener ,
        FlexibleAdapter.OnItemClickListener{
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private FlexibleAdapterExtension adapter;

    public FeedItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false); // Todo same view as player_list
        ButterKnife.bind(view);
        adapter = new FlexibleAdapterExtension<>(null,this);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this::updateData);
        updateData();
        initDataDatabase();
        return view;
    }

    public void initDataDatabase() {
        new FeedDbTask(items -> adapter.updateDataSet(items)).execute();
    }

    public void updateData() {
        refreshLayout.setRefreshing(true);
        Single.fromCallable(FeedNetworkTask::getFeedItems) // Todo refactor
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items->adapter.addItems(0,items));
    }

    public static FeedItemFragment newInstance(){
        return new FeedItemFragment();
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = adapter.getItem(position);
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
    public void onDataUpdated() {
        refreshLayout.setRefreshing(false);
    }
}
