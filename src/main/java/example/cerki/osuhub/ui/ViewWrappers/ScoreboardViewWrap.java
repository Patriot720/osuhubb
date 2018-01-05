package example.cerki.osuhub.ui.ViewWrappers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;

/**
 * Created by cerki on 03.01.2018.
 */

@SuppressWarnings("unchecked")
public class ScoreboardViewWrap
implements FlexibleAdapterExtension.OnDataUpdatedListener
{
    private final FlexibleAdapterExtension adapter;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public ScoreboardViewWrap(FrameLayout scoreboardFrame,FlexibleAdapterExtension adapter) {
        this.adapter = adapter;
        adapter.addListener(this);
        ButterKnife.bind(this,scoreboardFrame);
        mRecycler.setAdapter(adapter);
    }


    public boolean isUpdating(){
        return mRecycler.getVisibility() == View.GONE;
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

    @Override
    public void onDataUpdated() {
        setUpdating(false);
    }
}
