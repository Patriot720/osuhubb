package example.cerki.osuhub.Feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.cerki.osuhub.Feed.FeedItemFragment.OnListFragmentInteractionListener;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFeedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFeedItemRecyclerViewAdapter.ViewHolder> {

    private final List<FeedItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyFeedItemRecyclerViewAdapter(List<FeedItem> items, OnListFragmentInteractionListener listener, Context mContext) {
        mValues = items;
        mListener = listener;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feeditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        FeedItem item = mValues.get(position);
        holder.mPerformance.setText(String.format("%sPP", item.score.getAsInt(Score.PP)));
        holder.mAccuracy.setText(Util.getAccuracyString(item.score));
        holder.mMissCount.setText(String.format("%sxMiss", item.score.get(Score.MISS)));
        holder.mCombo.setText(String.format("%s/%s", item.score.get(Score.COMBO), item.beatmap.get(Beatmap.MAX_COMBO)));
        holder.mMods.setText("WIP"); // TODO create enum for this
        holder.mUsername.setText(item.username);
        holder.mMapName.setText(String.format("%s[%s]",item.beatmap.get(Beatmap.MAP_NAME),item.beatmap.get(Beatmap.DIFFICULTY_NAME)));
        holder.mStarRate.setText(item.beatmap.get(Beatmap.STAR_RATING));// Todo fix these floats
        Util.setImageFromAsset(mContext,holder.mRank,item.score.get(Score.RANK) + ".png"); // Todo :thinking:
        // TODO rank
        // TODO cover


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FeedItem mItem;
        public TextView mPerformance;
        public TextView mCombo;
        public TextView mUsername;
        public TextView mMapName; // TODO bad naming it's map_name + difficulty name
        public TextView mMissCount;
        public TextView mMods;
        public TextView mAccuracy;
        public TextView mStarRate;
        public ImageView mCover;
        public ImageView mRank;


        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }
}
