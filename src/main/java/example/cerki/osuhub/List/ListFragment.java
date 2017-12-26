package example.cerki.osuhub.List;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import java.util.ArrayList;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.GenericRecyclerFragment;
import example.cerki.osuhub.MainActivity;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cerki on 25.12.2017.
 */

@SuppressWarnings("unchecked")
public class ListFragment extends GenericRecyclerFragment<User>{
    @Override
    public boolean setListener(MainActivity context) {
        setListener(context::onListFragmentInteraction);
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_player_list;
    }

    @Override
    public void addListeners() {
        FlexibleAdapter adapter = getAdapter();
        adapter.setEndlessScrollListener(new FlexibleAdapter.EndlessScrollListener() {
            @Override
            public void noMoreLoad(int newItemsSize) {
                Log.e("WTF","WtfA;");
            }

            @Override
            public void onLoadMore(int lastPosition, int currentPage) {
                new Task(adapter::onLoadMoreComplete).loadUsersFromPage(currentPage+1);
            }
        },new ProgressItem()).setEndlessScrollThreshold(10).setEndlessPageSize(50);
    }

    @Override
    public void initDataDatabase() {
        Single.fromCallable(()-> ApiDatabase.getInstance().userDao().getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)->{
                    onUpdate(items);
                    updateData();
                });
    }
    @Override
    public void updateData() {
        new Task(this::onUpdate).loadUsers();
    }
}
