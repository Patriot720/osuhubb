package example.cerki.osuhub.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;

/**
 * Created by cerki on 04.01.2018.
 */

public abstract class GenericRecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,FlexibleAdapterExtension.OnDataUpdatedListener{
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private FlexibleAdapterExtension adapter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public FlexibleAdapterExtension getAdapter() {
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
        ButterKnife.bind(this,view);
        adapter = new FlexibleAdapterExtension<>(null,this);
        adapter.setEndlessPageSize(getAdapterPageSize());
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);

        return view;
    }
    protected int getAdapterPageSize(){
        return 0;
    }

    @Override
    public void onDataUpdated() {
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

}
