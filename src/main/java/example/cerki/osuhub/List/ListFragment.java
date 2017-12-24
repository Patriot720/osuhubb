package example.cerki.osuhub.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
@SuppressWarnings("unchecked")
public class ListFragment extends android.support.v4.app.Fragment {

    private OnListFragmentInteractionListener mListener;
    private FlexibleAdapter mAdapter;
    private List<User> mData;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
    }

    @SuppressWarnings("unused")
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initData(){
        Single.fromCallable(()-> ApiDatabase.getInstance().userDao().getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)->{
                mAdapter.updateDataSet(items,true);
                mRefresh.setRefreshing(false);
                if(Util.isNetworkAvailable(getContext()))
                    updateData();
        });
    }
    private void updateData() {
        mRefresh.setRefreshing(true);
        mData = new ArrayList<>();
        new Task((users -> {
            mAdapter.updateDataSet(users,true);
            mRefresh.setRefreshing(false);
        })).loadUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
            if(view instanceof SwipeRefreshLayout){
                mRefresh = (SwipeRefreshLayout) view;
                mRefresh.setRefreshing(true);
                mRefresh.setOnRefreshListener(this::updateData);
            }
            Context context = view.getContext();
            mRecycler = view.findViewById(R.id.list);
            mRecycler.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new FlexibleAdapter<>(mData);
            setListeners();
            mRecycler.setAdapter(mAdapter);
            initData();
        return view;
    }

    private void setListeners() {
        mAdapter.addListener((FlexibleAdapter.OnItemClickListener) position -> {
            mListener.onListFragmentInteraction(mAdapter.getItem(position));
            return true;
        });
        mAdapter.setEndlessScrollListener(new FlexibleAdapter.EndlessScrollListener() {
            @Override
            public void noMoreLoad(int newItemsSize) {
                Log.e("WTF","WtfA;");
            }

            @Override
            public void onLoadMore(int lastPosition, int currentPage) {
                new Task(items-> mAdapter.onLoadMoreComplete(items)).loadUsersFromPage(currentPage+1);
            }
        },new ProgressItem()).setEndlessScrollThreshold(10).setEndlessPageSize(50);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecentPlayInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(IFlexible player);
    }
}
