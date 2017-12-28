package example.cerki.osuhub;

import android.annotation.SuppressLint;
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


@SuppressWarnings("unchecked")
public class GenericRecyclerFragment<T extends AbstractFlexibleItem> extends Fragment
{

    interface OnClickListener<T extends AbstractFlexibleItem>{
        void onItemClick(T item);
    }
    interface WorkerInterface {
        void initData(GenericRecyclerFragment fragment);
        void updateData(GenericRecyclerFragment fragment);
    }

    private int layoutResource;
    private FlexibleAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;
    private View mProgressBar;
    private RecyclerView mRecycler;
    private OnClickListener<T> listener;
    private FlexibleAdapter.EndlessScrollListener endlessScroll;
    private WorkerInterface worker;
    private int pageSize;

    protected void doAfterViewCreated() {}
    protected int getFabButtonResource(){
       return  R.drawable.common_full_open_on_phone;
    }
    protected void onFabButtonClick(View view){
    }
    // Todo fab button

    @SuppressLint("ValidFragment")
    public GenericRecyclerFragment(int layoutResource, OnClickListener<T> listener, FlexibleAdapter.EndlessScrollListener endlessScroll, WorkerInterface worker, int pageSize) {
        this.layoutResource = layoutResource;
        this.listener = listener;
        this.endlessScroll = endlessScroll;
        this.worker = worker;
        this.pageSize = pageSize;
    }

    public GenericRecyclerFragment() {}

    public void setOnClickListener(){
        mAdapter.addListener((FlexibleAdapter.OnItemClickListener) position -> {
            listener.onItemClick((T) mAdapter.getItem(position));
            return true;
        });
    }

    public void setEndlessScroll(){
        mAdapter.setEndlessScrollListener(endlessScroll,new ProgressItem())
                .setEndlessPageSize(pageSize);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResource, container, false);
        setRetainInstance(true);
        mAdapter = new FlexibleAdapter<>(new ArrayList<>());
        setupRefresh(view);
        setupRecyclerView(view);
        setupProgressBar(view);
        worker.initData(this);
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

    private void updateDataWrap(){
        if(!Util.isNetworkAvailable(getContext()))
            return;
        worker.updateData(this);
    }

    public void onUpdate(List<T> items){
        setUpdating(false);
        mAdapter.updateDataSet(items);
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

    public FlexibleAdapter getAdapter() {
        return mAdapter;
    }
}
