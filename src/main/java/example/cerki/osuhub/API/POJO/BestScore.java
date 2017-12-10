package example.cerki.osuhub.API.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cerki on 10-Dec-17.
 */

@Entity
public class BestScore {
        @SerializedName("beatmap_id")
        @Expose
        private String beatmapId;
        @SerializedName("score")
        @Expose
        private String score;
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

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int timestamp;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


        public String getBeatmapId() {
            return beatmapId;
        }

        public void setBeatmapId(String beatmapId) {
            this.beatmapId = beatmapId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
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

        BestScore bestScore = (BestScore) o;

        if (beatmapId != null ? !beatmapId.equals(bestScore.beatmapId) : bestScore.beatmapId != null)
            return false;
        if (score != null ? !score.equals(bestScore.score) : bestScore.score != null) return false;
        if (userId != null ? !userId.equals(bestScore.userId) : bestScore.userId != null)
            return false;
        return date != null ? date.equals(bestScore.date) : bestScore.date == null;
    }

    @Override
    public int hashCode() {
        int result = beatmapId != null ? beatmapId.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
