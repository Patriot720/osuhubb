package example.cerki.osuhub.Feed;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import example.cerki.osuhub.Converters;
import example.cerki.osuhub.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


@Entity
@TypeConverters({Converters.class})
public class FeedItem extends AbstractFlexibleItem<FeedItem.FeedViewHolder> implements  Comparable<FeedItem>{
    public String coverUrl;
    public String username;
    public String relativeDate;
    public String performance;
    public String accuracy;
    public String combo;
    public String mods;
    public String mapName;
    public String starRate;
    public String missCount;
    public int rankResource;
    public Date date;

    @PrimaryKey(autoGenerate = true)
    int id;
    public String rankURI;
    public String rank;

    public FeedItem() {

    }

    @Override
    public int compareTo(@NonNull FeedItem feedItem) { // TODO sort by other things than date;
        if(date == null || feedItem.date == null)
            return 0;
        return feedItem.date.compareTo(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedItem feedItem = (FeedItem) o;

        if (coverUrl != null ? !coverUrl.equals(feedItem.coverUrl) : feedItem.coverUrl != null)
            return false;
        if (username != null ? !username.equals(feedItem.username) : feedItem.username != null)
            return false;
        if (performance != null ? !performance.equals(feedItem.performance) : feedItem.performance != null)
            return false;
        if (accuracy != null ? !accuracy.equals(feedItem.accuracy) : feedItem.accuracy != null)
            return false;
        if (combo != null ? !combo.equals(feedItem.combo) : feedItem.combo != null) return false;
        if (mods != null ? !mods.equals(feedItem.mods) : feedItem.mods != null) return false;
        if (mapName != null ? !mapName.equals(feedItem.mapName) : feedItem.mapName != null)
            return false;
        if (starRate != null ? !starRate.equals(feedItem.starRate) : feedItem.starRate != null)
            return false;
        return missCount != null ? missCount.equals(feedItem.missCount) : feedItem.missCount == null;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_feeditem;
    }

    @Override
    public FeedViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new FeedViewHolder(view,adapter);
    }


    @Override
    public void bindViewHolder(FlexibleAdapter adapter, FeedViewHolder holder, int position, List payloads) {

        holder.mPerformance.setText(performance);
        holder.mAccuracy.setText(accuracy);
        holder.mMissCount.setText(missCount);
        holder.mCombo.setText(combo);
        holder.mMods.setText(mods);
        holder.mUsername.setText(username);
        holder.mMapName.setText(mapName);
        holder.mStarRate.setText(starRate); // TODO Calculate for MODS
        holder.mRelativeDate.setText(relativeDate);
        Glide.with(holder.itemView)
                .load(Rank.getRankResourceId(rank))
                .into(holder.mRank);
        Glide.with(holder.itemView)
                .load(coverUrl)
                .transition(withCrossFade())
                .into(holder.mCover);
    }

    @Override
    public int hashCode() {
        int result = coverUrl != null ? coverUrl.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (performance != null ? performance.hashCode() : 0);
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        result = 31 * result + (combo != null ? combo.hashCode() : 0);
        result = 31 * result + (mods != null ? mods.hashCode() : 0);
        result = 31 * result + (mapName != null ? mapName.hashCode() : 0);
        result = 31 * result + (starRate != null ? starRate.hashCode() : 0);
        result = 31 * result + (missCount != null ? missCount.hashCode() : 0);
        return result;
    }
    public class FeedViewHolder extends FlexibleViewHolder{
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
        public FeedViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
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
            mRank = view.findViewById(R.id.rank);        }
    }


}
