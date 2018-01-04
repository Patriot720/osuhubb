package example.cerki.osuhub.ui.Fragments;

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
import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.POJO.User;
import example.cerki.osuhub.Logic.Tasks.ListTask;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class ListFragment extends Fragment
implements FlexibleAdapterExtension.OnDataUpdatedListener ,
        FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.EndlessScrollListener{
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private FlexibleAdapterExtension adapter;


    protected void onItemClick(User item) {
        android.support.v4.app.Fragment fragment = PlayerFragment.newInstance(item.getUserId(), item.getUsername());
        getFragmentManager().beginTransaction().add(R.id.content_main, fragment)
                .addToBackStack("stack")
                .commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
        ButterKnife.bind(view);
        adapter = new FlexibleAdapterExtension<>(null,this);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this::updateData);
        refreshLayout.setRefreshing(true);
        updateData();
        initDataDatabase();
        return view;
    }

    public void initDataDatabase() {
        Single.fromCallable(()-> ApiDatabase.getInstance().userDao().getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)-> adapter.updateDataSet(items));
    }

    public void updateData() {
        new ListTask(adapter::onLoadMoreComplete).loadUsers();
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void noMoreLoad(int newItemsSize) {}

    public void onLoadMore(int lastPosition, int currentPage) {
        new ListTask((items)->adapter.onLoadMoreComplete(items)).loadUsersFromPage(currentPage+1);
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = adapter.getItem(position);
        if(item instanceof User)
            onItemClick((User) item);
        return true;
    }

    @Override
    public void onDataUpdated() {
    refreshLayout.setRefreshing(false);
    }

}
