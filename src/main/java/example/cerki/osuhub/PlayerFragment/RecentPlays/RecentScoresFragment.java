package example.cerki.osuhub.PlayerFragment.RecentPlays;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.Feed.FeedItem;
import example.cerki.osuhub.Feed.FeedItemFragment;
import example.cerki.osuhub.List.ProgressItem;
import example.cerki.osuhub.R;
import io.reactivex.schedulers.Schedulers;
/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
@SuppressWarnings("unchecked")
public class RecentScoresFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private static final java.lang.String ARG_USERNAME = "username" ;
    private int mUserId = 0;
    private FeedItemFragment.OnListFragmentInteractionListener mListener;
    private FlexibleAdapter mAdapter;
    private String mUsername;
    private SwipeRefreshLayout mRefresh;
    private int counter;
    private List<BestScore> mItems;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecentScoresFragment() {
    }

    @SuppressWarnings("unused")
    public static RecentScoresFragment newInstance(int userId,String username) {
        RecentScoresFragment fragment = new RecentScoresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_USERNAME,username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_USER_ID);
            mUsername = getArguments().getString(ARG_USERNAME);
        }
    }

    public void initAsync(){
        OsuAPI.getApi().getRecentScoresBy(mUserId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(items ->{
                    new RecentScoresTask(feedItems -> {
                        mAdapter.updateDataSet(feedItems);
                        mRefresh.setRefreshing(false);
                    },items).execute(0);
                    mItems = items;
                });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feeditem_list, container, false);
        // Set the adapter
        // TODO refactor with FeedFragment make it one
            mRefresh = view.findViewById(R.id.list);
            mRefresh.setOnRefreshListener(this::initAsync);
            Context context = view.getContext();
            RecyclerView recyclerView = view.findViewById(R.id.feedlist);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            ArrayList<FeedItem> recentScores = new ArrayList<>();
            mAdapter = new FlexibleAdapter(recentScores);
            recyclerView.setAdapter(mAdapter);
            mRefresh.setRefreshing(true);
            initAsync();
            setAdapterListeners();
        return view;
    }

    public void setAdapterListeners(){
        mAdapter.setEndlessScrollListener(new FlexibleAdapter.EndlessScrollListener() {
            @Override
            public void noMoreLoad(int newItemsSize) {
            }

            @Override
            public void onLoadMore(int lastPosition, int currentPage) {
                new RecentScoresTask(feedItems -> mAdapter.onLoadMoreComplete(feedItems),mItems).execute(lastPosition);
            }
        },new ProgressItem()).setEndlessPageSize(10);
        mAdapter.addListener(new FlexibleAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(int position) {
                mListener.onListFragmentInteraction((FeedItem) mAdapter.getItem(position));
                return true;
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedItemFragment.OnListFragmentInteractionListener) {
            mListener = (FeedItemFragment.OnListFragmentInteractionListener) context;
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

}
