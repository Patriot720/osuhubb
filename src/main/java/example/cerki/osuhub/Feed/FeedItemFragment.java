package example.cerki.osuhub.Feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FeedItemFragment extends Fragment {


    private OnListFragmentInteractionListener mListener;
    private List<FeedItem> mData;
    private FlexibleAdapter<FeedItem> mAdapter;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        mData = new ArrayList<>();
        new NewFeedTaskDb(items -> {
            mAdapter.updateDataSet(items);
            mRefresh.setRefreshing(false);
            if(Util.isNetworkAvailable(getContext()))
                updateData();
        }).execute();
    }
    void updateData(){
        mRefresh.setRefreshing(true);
        new NewFeedTaskNetwork(items -> {
            mAdapter.updateDataSet(items,true);
            mRefresh.setRefreshing(false);
        }).execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeditem_list, container, false);
        if(view instanceof SwipeRefreshLayout){
            mRefresh = (SwipeRefreshLayout) view;
            mRefresh.setRefreshing(true);
            mRefresh.setOnRefreshListener(this::updateData);
        }
            Context context = view.getContext();
            mRecycler = view.findViewById(R.id.feedlist);
            mRecycler.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new FlexibleAdapter<>(mData);
            addListeners();
            mRecycler.setHasFixedSize(true);
            mRecycler.setItemViewCacheSize(20);
            mRecycler.setDrawingCacheEnabled(true);
            mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
            mRecycler.setAdapter(mAdapter);
            initData();
        return view;
    }

    private void addListeners() {
        mAdapter.addListener((FlexibleAdapter.OnItemClickListener) position -> {
            FeedItem item = mAdapter.getItem(position);
            mListener.onListFragmentInteraction(item);
            return true;
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
        void onListFragmentInteraction(FeedItem item);
    }
}
