package example.cerki.osuhub.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListFragment extends android.support.v4.app.Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerAdapter mAdapter;
    private List<Player> mData;
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
        if(this.isResumed()){
         getFromDb();   // Todo fix long white screen if app is unloaded;
        }
        initData();
    }

    private void getFromDb() {
    }

    private void initData() {
        mData = new ArrayList<>();
        PlayersTable table = new PlayersTable(new OsuDb(getContext()).getWritableDatabase());
        new Task(table,new WorkDoneListener() {
            @Override
            public void workDone(List<Player> players) {
                mAdapter.replaceData(players);
                mRefresh.setRefreshing(false);
                mRecycler.scheduleLayoutAnimation();
            }
        }).loadPlayers();
    }

    // TODO preload from DB and add last update thing at the top
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
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
            mRecycler = view.findViewById(R.id.list);
            mRecycler.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new RecyclerAdapter(getContext(),mData,mListener);
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
        void onListFragmentInteraction(Player player);
    }
}
