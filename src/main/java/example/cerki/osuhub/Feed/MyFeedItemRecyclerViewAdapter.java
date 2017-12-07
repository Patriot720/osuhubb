package example.cerki.osuhub.Feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.Feed.FeedItemFragment.OnListFragmentInteractionListener;
import example.cerki.osuhub.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
// TODO move to fastAdapter mb
    // TODO infinite SCroll one week at the time
    // TODO updates available thingy instead of reloading everything completely cache FeedItems
    // TODO preload from database first then ^^^
public class MyFeedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFeedItemRecyclerViewAdapter.ViewHolder> {

    private final List<FeedItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFeedItemRecyclerViewAdapter(List<FeedItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    // TODO extract This
void replaceData(final List<FeedItem> players){
        final List<FeedItem> oldData = new ArrayList<>(mValues);
        mValues.clear();

        if (players != null) {
            mValues.addAll(players);
        }

        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldData.size();
            }

            @Override
            public int getNewListSize() {
                return mValues.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldData.get(oldItemPosition).equals(mValues.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldData.get(oldItemPosition).equals(mValues.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feeditem, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedItem feedItem = mValues.get(viewHolder.getAdapterPosition());
                mListener.onListFragmentInteraction(feedItem);
            }
        });
        return viewHolder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        FeedItem item = mValues.get(position);
        holder.mPerformance.setText(item.performance);
        holder.mAccuracy.setText(item.accuracy);
        holder.mMissCount.setText(item.missCount);
        holder.mCombo.setText(item.combo);
        holder.mMods.setText(item.mods);
        holder.mUsername.setText(item.username);
        holder.mMapName.setText(item.mapName);
        holder.mStarRate.setText(item.starRate); // TODO Calculate for MODS
        holder.mRelativeDate.setText(item.relativeDate);
        Glide.with(holder.mView)
                .load(item.rankResource)
                .into(holder.mRank);
        Glide.with(holder.mView)
                .load(item.coverUrl)
                .transition(withCrossFade())
                .into(holder.mCover);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
        public TextView mRelativeDate;
        public ImageView mCover;
        public ImageView mRank;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPerformance = view.findViewById(R.id.pp);
            mCombo = view.findViewById(R.id.combo);
            mUsername = view.findViewById(R.id.username);
            mMapName = view.findViewById(R.id.map_name);
            mMissCount = view.findViewById(R.id.miss_count);
            mMods = view.findViewById(R.id.mods);
            mAccuracy = view.findViewById(R.id.acc);
            mStarRate = view.findViewById(R.id.star_rate);
            mRelativeDate = view.findViewById(R.id.relative_date);
            mCover = view.findViewById(R.id.cover);
            mRank = view.findViewById(R.id.rank);
        }

    }
}
