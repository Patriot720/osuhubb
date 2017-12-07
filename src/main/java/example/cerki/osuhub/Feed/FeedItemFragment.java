package example.cerki.osuhub.Feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FeedItemFragment extends Fragment {


    private OnListFragmentInteractionListener mListener;
    private List<FeedItem> mData;
    private MyFeedItemRecyclerViewAdapter mAdapter;
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
        initData();
    }

    private void initData() {
        mData = new ArrayList<>();
        new FeedTask(getContext(), new FeedTask.WorkDoneListener() {
            @Override
            public void workDone(List<FeedItem> items) {
                mAdapter.replaceData(items);
                mRefresh.setRefreshing(false);
                mRecycler.scheduleLayoutAnimation();
                // Todo animation schedule
            }
        }).execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeditem_list, container, false);

        // Set the adapter
        if(view instanceof SwipeRefreshLayout){
            mRefresh = (SwipeRefreshLayout) view;
            mRefresh.setRefreshing(true);
            mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initData();
                }
            });
        }
            Context context = view.getContext();
            mRecycler = view.findViewById(R.id.feedlist);
            mRecycler.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new MyFeedItemRecyclerViewAdapter(mData,mListener);
            mRecycler.setHasFixedSize(true);
            mRecycler.setItemViewCacheSize(20);
            mRecycler.setDrawingCacheEnabled(true);
            mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
            mRecycler.setAdapter(mAdapter);
        return view;
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
