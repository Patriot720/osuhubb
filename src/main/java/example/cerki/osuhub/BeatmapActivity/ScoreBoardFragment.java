package example.cerki.osuhub.BeatmapActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Score;
import example.cerki.osuhub.R;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ScoreBoardFragment extends Fragment {

    private static final String ARG_BEATMAP_ID = "mBeatmapId";
    private int mBeatmapId = 0;
    private OnListFragmentInteractionListener mListener;
    private List<Score> mData;
    private FlexibleAdapter<Score> mAdapter;
    private RecyclerView mRecycler;
    private ProgressBar mProgressBar;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ScoreBoardFragment() {
    }

    @SuppressWarnings("unused")
    public static ScoreBoardFragment newInstance(int beatmap_id) {
        ScoreBoardFragment fragment = new ScoreBoardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BEATMAP_ID, beatmap_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mBeatmapId = getArguments().getInt(ARG_BEATMAP_ID);
        }
    }
    public void initData(){
        OsuAPI.getApi().getScoresBy(mBeatmapId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)-> {
                    Log.v("tag","WAT " + items.size());
            mAdapter.updateDataSet(items);
            setUpdating(false);
                });
    }
    public void setUpdating(boolean bool){
        if(bool){
            mRecycler.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else{
            mRecycler.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }
   public boolean isUpdating(){
       return mRecycler.getVisibility() == View.GONE;
   }

    public FlexibleAdapter<Score> getmAdapter() {
        return mAdapter;
    }

    public void updateData(List<Score> scores){
        mAdapter.updateDataSet(scores);
        setUpdating(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard_list, container, false);
        // Set the adapter
            Context context = view.getContext();
            mProgressBar = view.findViewById(R.id.progress_bar);
            mRecycler = view.findViewById(R.id.recycler);
            mData = new ArrayList<>();
            mAdapter = new FlexibleAdapter<>(mData);
            mRecycler.setLayoutManager(new LinearLayoutManager(context));
            mRecycler.setAdapter(mAdapter);
            initData();
        return view;
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
        void onListFragmentInteraction(Score item);
    }
}
