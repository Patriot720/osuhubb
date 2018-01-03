package example.cerki.osuhub.ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import example.cerki.osuhub.List.ProgressItem;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;


@SuppressWarnings("unchecked")
public abstract class GenericRecyclerFragment<T extends AbstractFlexibleItem> extends Fragment
implements FlexibleAdapter.EndlessScrollListener
{
    private static final String ARG_BEATMAP_ID = "beatmapId" ;
    private FlexibleAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;
    private static final String ARG_USER_ID = "userId" ;
    private static final String ARG_USERNAME = "username" ;
    private int userId = 0;
    private String username = "";
    private int beatmapId = 0;
    private View mProgressBar;
    private RecyclerView mRecycler;

    protected abstract int getEndlessScrollPageSize();
    protected abstract int getLayoutResource();
    public abstract void initDataDatabase();
    public abstract void updateData();
    protected abstract void onItemClick(T item);
    protected void doAfterViewCreated() {}
    protected int getFabButtonResource(){
       return  R.drawable.common_full_open_on_phone;
    };
    protected void onFabButtonClick(View view){
    }


    public GenericRecyclerFragment() {}

    public void setOnClickListener(){
        mAdapter.addListener((FlexibleAdapter.OnItemClickListener) position -> {
            onItemClick((T) mAdapter.getItem(position));
            return true;
        });
    }

    public void setEndlessScroll(){
        mAdapter.setEndlessScrollListener(this,new ProgressItem())
                .setEndlessPageSize(getEndlessScrollPageSize());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        mAdapter = new FlexibleAdapter<>(new ArrayList<>());
        setupRefresh(view);
        setupRecyclerView(view);
        setupProgressBar(view);
        initDataDatabase();
        setOnClickListener();
        setEndlessScroll();
        setupFabButton();
        doAfterViewCreated();
        return view;
    }

    private void setupFabButton() {
        FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
        if (fab == null) return;
        fab.setImageResource(getFabButtonResource()); // todo change this
        fab.setOnClickListener(this::onFabButtonClick);
    }

    private void setupProgressBar(View view){
        mProgressBar = view.findViewById(R.id.progress_bar);
        if(mProgressBar == null)
            mProgressBar = new ProgressBar(view.getContext());
    }
    private void setupRefresh(View view) {
        mRefresh =  view.findViewById(R.id.refresh);
        if(mRefresh == null){
            mRefresh = new SwipeRefreshLayout(view.getContext());
            return;
        }
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(this::updateDataWrap);
    }

    private void setupRecyclerView(View view) {
        mRecycler = view.findViewById(R.id.list);
        if(mRecycler == null)
            return;
        Context context = view.getContext();
        mRecycler.setLayoutManager(new LinearLayoutManager(context));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemViewCacheSize(20);
        mRecycler.setDrawingCacheEnabled(true);
        mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecycler.setAdapter(mAdapter);
    }


    public void setBeatmapId(int beatmapId){
        Bundle arguments = getArguments();
        if(arguments == null)
            return;
        arguments.putInt(ARG_BEATMAP_ID,beatmapId);
        setArguments(arguments);
    }
    public void setArguments(int userId,String username){
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_USER_ID,userId);
        bundle.putString(ARG_USERNAME,username);
        setArguments(bundle);
    }
    public void setArguments(int userId){
        setArguments(userId,"");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
            username = getArguments().getString(ARG_USERNAME);
            beatmapId = getArguments().getInt(ARG_BEATMAP_ID);
        }
    }
    private void updateDataWrap(){
        if(!Util.isNetworkAvailable(getContext()))
            return;
        updateData();
    }

    @Override
    public void noMoreLoad(int newItemsSize) {
    }

    public void onUpdate(List<T> items){
        setUpdating(false);
        mAdapter.updateDataSet(items);
    }

    public int getUserId() {
        return userId;
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
        mRefresh.setRefreshing(bool);
    }
    public void addItems(int position, List<T> items){
        mAdapter.addItems(0, items);
        setUpdating(false);
    }
    public void addItemsOnTop(List<T> items){
        addItems(0,items);
    }

    public String getUsername() {
        return username;
    }

    public FlexibleAdapter getAdapter() {
        return mAdapter;
    }

}
