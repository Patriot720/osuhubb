package example.cerki.osuhub.ui.ViewWrappers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.cerki.osuhub.R;

/**
 * Created by cerki on 03.01.2018.
 */

public class ScoreboardViewWrap {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public ScoreboardViewWrap(FrameLayout scoreboardFrame) {
        ButterKnife.bind(scoreboardFrame);
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
}
