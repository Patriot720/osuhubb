package example.cerki.osuhub.API.POJO;

import android.view.View;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import butterknife.BindView;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import example.cerki.osuhub.R;

/**
 * Created by cerki on 17.12.2017.
 */
public class Score extends AbstractFlexibleItem<Score.ViewHolder>{
        @SerializedName("score_id")
        @Expose
        private String scoreId;
        @SerializedName("score")
        @Expose
        private String score;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("maxcombo")
        @Expose
        private String maxcombo;
        @SerializedName("count50")
        @Expose
        private String count50;
        @SerializedName("count100")
        @Expose
        private String count100;
        @SerializedName("count300")
        @Expose
        private String count300;
        @SerializedName("countmiss")
        @Expose
        private String countmiss;
        @SerializedName("countkatu")
        @Expose
        private String countkatu;
        @SerializedName("countgeki")
        @Expose
        private String countgeki;
        @SerializedName("perfect")
        @Expose
        private String perfect;
        @SerializedName("enabled_mods")
        @Expose
        private String enabledMods;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("pp")
        @Expose
        private String pp;

        public String getScoreId() {
            return scoreId;
        }

        public void setScoreId(String scoreId) {
            this.scoreId = scoreId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMaxcombo() {
            return maxcombo;
        }

        public void setMaxcombo(String maxcombo) {
            this.maxcombo = maxcombo;
        }

        public String getCount50() {
            return count50;
        }

        public void setCount50(String count50) {
            this.count50 = count50;
        }

        public String getCount100() {
            return count100;
        }

        public void setCount100(String count100) {
            this.count100 = count100;
        }

        public String getCount300() {
            return count300;
        }

        public void setCount300(String count300) {
            this.count300 = count300;
        }

        public String getCountmiss() {
            return countmiss;
        }

        public void setCountmiss(String countmiss) {
            this.countmiss = countmiss;
        }

        public String getCountkatu() {
            return countkatu;
        }

        public void setCountkatu(String countkatu) {
            this.countkatu = countkatu;
        }

        public String getCountgeki() {
            return countgeki;
        }

        public void setCountgeki(String countgeki) {
            this.countgeki = countgeki;
        }

        public String getPerfect() {
            return perfect;
        }

        public void setPerfect(String perfect) {
            this.perfect = perfect;
        }

        public String getEnabledMods() {
            return enabledMods;
        }

        public void setEnabledMods(String enabledMods) {
            this.enabledMods = enabledMods;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPp() {
            return pp;
        }

        public void setPp(String pp) {
            this.pp = pp;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        return scoreId != null ? scoreId.equals(score.scoreId) : score.scoreId == null;
    }

    @Override
    public int hashCode() {
        return scoreId != null ? scoreId.hashCode() : 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.scoreboard_entity;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
            return new ViewHolder(view,adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ViewHolder holder, int position, List payloads) {
        holder.performance.setText(pp);
        holder.username.setText(username);
    }

    public class ViewHolder extends FlexibleViewHolder{
        TextView performance;
        TextView username;
        public ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            performance = view.findViewById(R.id.performance);
            username = view.findViewById(R.id.username);
        }
    }
}
