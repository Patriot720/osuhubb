package example.cerki.osuhub.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.POJO.User;
import example.cerki.osuhub.Logic.Tasks.ListTask;
import example.cerki.osuhub.R;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class ListFragment extends GenericRecyclerFragment
implements
        FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.EndlessScrollListener{

    public void initDataDatabase() {
        Single.fromCallable(()-> ApiDatabase.getInstance().userDao().getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)-> getAdapter().updateDataSet(items));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        updateData();
        initDataDatabase();
    }

    public void updateData() {
        new ListTask(getAdapter()::updateDataSet).loadUsers();
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    protected int getAdapterPageSize() {
        return 50;
    }

    @Override
    public void noMoreLoad(int newItemsSize) {}

    public void onLoadMore(int lastPosition, int currentPage) {
        new ListTask((items)->getAdapter().onLoadMoreComplete(items)).loadUsersFromPage(currentPage+1);
    }

    @Override
    public boolean onItemClick(int position) {
        IFlexible item = getAdapter().getItem(position);
        if(item instanceof User)
        {
            User user = (User) item;
            android.support.v4.app.Fragment fragment = PlayerFragment.newInstance(user.getUserId(), user.getUsername());
            getFragmentManager().beginTransaction().add(R.id.content_main, fragment)
                    .addToBackStack("stack")
                    .commit();
        }
        return true;
    }

    @Override
    public void onDataUpdated() {
    refreshLayout.setRefreshing(false);
    progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        updateData();
    }
}
