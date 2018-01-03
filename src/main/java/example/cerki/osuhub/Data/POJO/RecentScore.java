package example.cerki.osuhub.Data.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cerki on 23.12.2017.
 */

public class RecentScore {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentScore that = (RecentScore) o;

        if (beatmapId != null ? !beatmapId.equals(that.beatmapId) : that.beatmapId != null)
            return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (maxcombo != null ? !maxcombo.equals(that.maxcombo) : that.maxcombo != null)
            return false;
        if (count50 != null ? !count50.equals(that.count50) : that.count50 != null) return false;
        if (count100 != null ? !count100.equals(that.count100) : that.count100 != null)
            return false;
        if (count300 != null ? !count300.equals(that.count300) : that.count300 != null)
            return false;
        if (countmiss != null ? !countmiss.equals(that.countmiss) : that.countmiss != null)
            return false;
        if (countkatu != null ? !countkatu.equals(that.countkatu) : that.countkatu != null)
            return false;
        if (countgeki != null ? !countgeki.equals(that.countgeki) : that.countgeki != null)
            return false;
        if (perfect != null ? !perfect.equals(that.perfect) : that.perfect != null) return false;
        if (enabledMods != null ? !enabledMods.equals(that.enabledMods) : that.enabledMods != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return rank != null ? rank.equals(that.rank) : that.rank == null;
    }

    @Override
    public int hashCode() {
        int result = beatmapId != null ? beatmapId.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (maxcombo != null ? maxcombo.hashCode() : 0);
        result = 31 * result + (count50 != null ? count50.hashCode() : 0);
        result = 31 * result + (count100 != null ? count100.hashCode() : 0);
        result = 31 * result + (count300 != null ? count300.hashCode() : 0);
        result = 31 * result + (countmiss != null ? countmiss.hashCode() : 0);
        result = 31 * result + (countkatu != null ? countkatu.hashCode() : 0);
        result = 31 * result + (countgeki != null ? countgeki.hashCode() : 0);
        result = 31 * result + (perfect != null ? perfect.hashCode() : 0);
        result = 31 * result + (enabledMods != null ? enabledMods.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }

}
