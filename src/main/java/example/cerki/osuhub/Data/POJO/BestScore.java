package example.cerki.osuhub.Data.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import example.cerki.osuhub.Data.ApiDatabase.Converters;
import example.cerki.osuhub.Util.Util;

@Entity(primaryKeys = {"beatmapId","userId"})
@TypeConverters({Converters.class})
public class BestScore implements Serializable{
        @SerializedName("beatmap_id")
        @Expose
        private int beatmapId;
        @SerializedName("score")
        @Expose
        private String score;
        @SerializedName("maxcombo")
        @Expose
        private int maxcombo;
        @SerializedName("count50")
        @Expose
        private int count50;
        @SerializedName("count100")
        @Expose
        private int count100;
        @SerializedName("count300")
        @Expose
        private int count300;
        @SerializedName("countmiss")
        @Expose
        private int countmiss;
        @SerializedName("countkatu")
        @Expose
        private int countkatu;
        @SerializedName("countgeki")
        @Expose
        private int countgeki;
        @SerializedName("perfect")
        @Expose
        private String perfect;
        @SerializedName("enabled_mods")
        @Expose
        private String enabledMods;
        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("date")
        @Expose
        private Date date;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("pp")
        @Expose
        private Float pp;



    public int getBeatmapId() {
            return beatmapId;
        }

        public void setBeatmapId(int beatmapId) {
            this.beatmapId = beatmapId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getMaxcombo() {
            return maxcombo;
        }

        public void setMaxcombo(int maxcombo) {
            this.maxcombo = maxcombo;
        }

        public int getCount50() {
            return count50;
        }

        public void setCount50(int count50) {
            this.count50 = count50;
        }

        public int getCount100() {
            return count100;
        }

        public void setCount100(int count100) {
            this.count100 = count100;
        }

        public int getCount300() {
            return count300;
        }

        public void setCount300(int count300) {
            this.count300 = count300;
        }

        public int getCountmiss() {
            return countmiss;
        }

        public void setCountmiss(int countmiss) {
            this.countmiss = countmiss;
        }

        public int getCountkatu() {
            return countkatu;
        }

        public void setCountkatu(int countkatu) {
            this.countkatu = countkatu;
        }

        public int getCountgeki() {
            return countgeki;
        }

        public void setCountgeki(int countgeki) {
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public Float getPp() {
            return pp;
        }

        public void setPp(Float pp) {
            this.pp = pp;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BestScore bestScore = (BestScore) o;

        if (beatmapId != bestScore.beatmapId) return false;
        if (userId != bestScore.userId) return false;
        if (score != null ? !score.equals(bestScore.score) : bestScore.score != null) return false;
        return date != null ? date.equals(bestScore.date) : bestScore.date == null;
    }

    @Ignore
    @Override
    public int hashCode() {
        int result = beatmapId;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public boolean isMonthOld() {
        Date monthOldDate = Util.getMonthOldDate();
        return date.compareTo(monthOldDate) > 0;
    }


    public boolean isWeekOld() {
        Date weekOldDate = Util.getWeekOldDate();
        return date.compareTo(weekOldDate) > 0;
    }

    @Override
    public String toString() {
        return pp.toString();
    }

    public boolean isNewerThan(Long realTimestamp) {
        return date.getTime() >= realTimestamp;
    }
}
