package example.cerki.osuhub;

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

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.R;


@SuppressWarnings("unchecked")
public abstract class GenericRecyclerFragment<T extends IFlexible> extends Fragment{
    private OnListFragmentInteractionListener mListener;
    private List<T> mData;
    private FlexibleAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecycler;
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(IFlexible item);
    }

    public OnListFragmentInteractionListener getListener() {
        return mListener;
    }

    public void setListener(OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public List<T> getDataList() {
        return mData;
    }

    public FlexibleAdapter<T> getAdapter() {
        return mAdapter;
    }

    public SwipeRefreshLayout getRefresher() {
        return mRefresh;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        if(view instanceof SwipeRefreshLayout){
            mRefresh = (SwipeRefreshLayout) view;
            mRecycler = view.findViewById(R.id.list);
            mAdapter = new FlexibleAdapter<>(mData);
            mRefresh.setRefreshing(true);
            mRefresh.setOnRefreshListener(this::updateData);
        }
        Context context = view.getContext();
        mRecycler.setLayoutManager(new LinearLayoutManager(context));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemViewCacheSize(20);
        mRecycler.setDrawingCacheEnabled(true);
        mRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        mRecycler.setAdapter(mAdapter);
        addListeners();
        initData();
        return view;
    }
    public abstract int getLayoutResource();
    public abstract void addListeners();
    public abstract void initData();
    public abstract void updateData();

    public GenericRecyclerFragment() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
