package example.cerki.osuhub.API.POJO;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;

/**
 * Created by cerki on 10-Dec-17.
 */

@Entity
public class User extends AbstractFlexibleItem<User.UserViewHolder>{
    @SerializedName("user_id")
    @Expose
    @PrimaryKey
    private int userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("count300")
    @Expose
    private String count300;
    @SerializedName("count100")
    @Expose
    private String count100;
    @SerializedName("count50")
    @Expose
    private String count50;
    @SerializedName("playcount")
    @Expose
    private int playcount;
    @SerializedName("ranked_score")
    @Expose
    private String rankedScore;
    @SerializedName("total_score")
    @Expose
    private String totalScore;
    @SerializedName("pp_rank")
    @Expose
    private int ppRank;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("pp_raw")
    @Expose
    private float ppRaw;
    @SerializedName("accuracy")
    @Expose
    private float accuracy;
    @SerializedName("count_rank_ss")
    @Expose
    private String countRankSs;
    @SerializedName("count_rank_s")
    @Expose
    private String countRankS;
    @SerializedName("count_rank_a")
    @Expose
    private String countRankA;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("pp_country_rank")
    @Expose
    private String ppCountryRank;
    @SerializedName("events")
    @Expose
    @Ignore
    private List<Event> events = null;
    @Ignore
    private float accuracy_difference;
    @Ignore
    private float performance_difference;
    @Ignore
    private int rank_difference;
    @Ignore
    private int playcount_difference;

    public float getAccuracy_difference() {
        return accuracy_difference;
    }

    public void setAccuracy_difference(float accuracy_difference) {
        this.accuracy_difference = accuracy_difference;
    }

    public float getPerformance_difference() {
        return performance_difference;
    }

    public void setPerformance_difference(float performance_difference) {
        this.performance_difference = performance_difference;
    }

    public int getRank_difference() {
        return rank_difference;
    }

    public void setRank_difference(int rank_difference) {
        this.rank_difference = rank_difference;
    }

    public int getPlaycount_difference() {
        return playcount_difference;
    }

    public void setPlaycount_difference(int playcount_difference) {
        this.playcount_difference = playcount_difference;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCount300() {
        return count300;
    }

    public void setCount300(String count300) {
        this.count300 = count300;
    }

    public String getCount100() {
        return count100;
    }

    public void setCount100(String count100) {
        this.count100 = count100;
    }

    public String getCount50() {
        return count50;
    }

    public void setCount50(String count50) {
        this.count50 = count50;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getRankedScore() {
        return rankedScore;
    }

    public void setRankedScore(String rankedScore) {
        this.rankedScore = rankedScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public int getPpRank() {
        return ppRank;
    }

    public void setPpRank(int ppRank) {
        this.ppRank = ppRank;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public float getPpRaw() {
        return ppRaw;
    }

    public void setPpRaw(float ppRaw) {
        this.ppRaw = ppRaw;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getCountRankSs() {
        return countRankSs;
    }

    public void setCountRankSs(String countRankSs) {
        this.countRankSs = countRankSs;
    }

    public String getCountRankS() {
        return countRankS;
    }

    public void setCountRankS(String countRankS) {
        this.countRankS = countRankS;
    }

    public String getCountRankA() {
        return countRankA;
    }

    public void setCountRankA(String countRankA) {
        this.countRankA = countRankA;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPpCountryRank() {
        return ppCountryRank;
    }

    public void setPpCountryRank(String ppCountryRank) {
        this.ppCountryRank = ppCountryRank;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void compare(User user){
        accuracy_difference = accuracy - user.accuracy;
        rank_difference =  user.ppRank - ppRank;
        performance_difference = ppRaw - user.ppRaw;
        playcount_difference = playcount - user.playcount;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + playcount;
        result = 31 * result + ppRank;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (ppRaw != +0.0f ? Float.floatToIntBits(ppRaw) : 0);
        result = 31 * result + (accuracy != +0.0f ? Float.floatToIntBits(accuracy) : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (playcount != user.playcount) return false;
        if (ppRank != user.ppRank) return false;
        if (Float.compare(user.ppRaw, ppRaw) != 0) return false;
        return Float.compare(user.accuracy, accuracy) == 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.players_top_item;
    }

    @Override
    public UserViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new UserViewHolder(view,adapter);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindViewHolder(FlexibleAdapter adapter, UserViewHolder holder, int position, List payloads) {
        holder.mUsernameView.setText(username);
        if(country != null)
        Glide.with(holder.itemView)
                .load("file:///android_asset/" + country)
                .into(holder.mCountryImage);
        holder.mPerformanceView.setText(String.valueOf(ppRaw));
        holder.mAccuracyView.setText(String.format("%s%%",accuracy));
        holder.mPlaycountView.setText(String.valueOf(playcount));
        holder.mRankView.setText(String.format("#%d",ppRank));
        Util.showPlayerDifference(
                holder.mPerformanceDifference,
                holder.mPerformanceArrow,
                performance_difference
        );
        Util.showPlayerDifference(
                holder.mAccuracyDifference,
                holder.mAccuracyArrow,
                accuracy_difference

        );
        Util.showPlayerDifference(
                holder.mRankDifference,
                holder.mRankArrow,
                rank_difference
        );
        Util.showPlayerDifference(
                holder.mPlaycountDifference,
                holder.mPlaycountArrow,
                playcount_difference
        );
    }

    public class UserViewHolder extends FlexibleViewHolder{
        public final TextView mUsernameView;
        public final TextView mRankView;
        public final TextView mPerformanceView;
        public final TextView mAccuracyView;
        public final TextView mPlaycountView;
        public final ImageView mCountryImage;

        public final TextView mPerformanceDifference;
        public final ImageView mPerformanceArrow;
        public final TextView mAccuracyDifference;
        public final ImageView mAccuracyArrow;
        public final TextView mPlaycountDifference;
        public final ImageView mPlaycountArrow;
        public final TextView mRankDifference;
        public final ImageView mRankArrow;
        public UserViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mUsernameView = view.findViewById(R.id.item_username);
            mRankView = view.findViewById(R.id.item_rank);
            mAccuracyView = view.findViewById(R.id.item_acc);
            mPerformanceView = view.findViewById(R.id.item_pp);
            mPlaycountView = view.findViewById(R.id.item_pc);
            mCountryImage = view.findViewById(R.id.item_country_image);

            mPerformanceDifference = view.findViewById(R.id.item_pp_difference);
            mPerformanceArrow = view.findViewById(R.id.item_pp_arrow);
            mAccuracyDifference = view.findViewById(R.id.item_acc_difference);
            mAccuracyArrow = view.findViewById(R.id.item_acc_arrow);
            mPlaycountDifference = view.findViewById(R.id.item_pc_difference);
            mPlaycountArrow = view.findViewById(R.id.item_pc_arrow);
            mRankDifference = view.findViewById(R.id.item_rank_difference);
            mRankArrow = view.findViewById(R.id.item_rank_arrow);
        }
    }
}
