package example.cerki.osuhub.API.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cerki on 09-Dec-17.
 */

@Entity
public class Beatmap {

    @SerializedName("beatmapset_id")
    @Expose
    private String beatmapsetId;
    @SerializedName("beatmap_id")
    @Expose
    @PrimaryKey
    private int beatmapId;
    @SerializedName("approved")
    @Expose
    private String approved;
    @SerializedName("total_length")
    @Expose
    private String totalLength;
    @SerializedName("hit_length")
    @Expose
    private String hitLength;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("file_md5")
    @Expose
    private String fileMd5;
    @SerializedName("diff_size")
    @Expose
    private String diffSize;
    @SerializedName("diff_overall")
    @Expose
    private String diffOverall;
    @SerializedName("diff_approach")
    @Expose
    private String diffApproach;
    @SerializedName("diff_drain")
    @Expose
    private String diffDrain;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("approved_date")
    @Expose
    private String approvedDate;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("bpm")
    @Expose
    private String bpm;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("genre_id")
    @Expose
    private String genreId;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("favourite_count")
    @Expose
    private String favouriteCount;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("passcount")
    @Expose
    private String passcount;
    @SerializedName("max_combo")
    @Expose
    private String maxCombo;
    @SerializedName("difficultyrating")
    @Expose
    private Float difficultyrating;

    public String getBeatmapsetId() {
        return beatmapsetId;
    }

    public void setBeatmapsetId(String beatmapsetId) {
        this.beatmapsetId = beatmapsetId;
    }

    public int getBeatmapId() {
        return beatmapId;
    }

    public void setBeatmapId(int beatmapId) {
        this.beatmapId = beatmapId;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(String totalLength) {
        this.totalLength = totalLength;
    }

    public String getHitLength() {
        return hitLength;
    }

    public void setHitLength(String hitLength) {
        this.hitLength = hitLength;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getDiffSize() {
        return diffSize;
    }

    public void setDiffSize(String diffSize) {
        this.diffSize = diffSize;
    }

    public String getDiffOverall() {
        return diffOverall;
    }

    public void setDiffOverall(String diffOverall) {
        this.diffOverall = diffOverall;
    }

    public String getDiffApproach() {
        return diffApproach;
    }

    public void setDiffApproach(String diffApproach) {
        this.diffApproach = diffApproach;
    }

    public String getDiffDrain() {
        return diffDrain;
    }

    public void setDiffDrain(String diffDrain) {
        this.diffDrain = diffDrain;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(String favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public String getPasscount() {
        return passcount;
    }

    public void setPasscount(String passcount) {
        this.passcount = passcount;
    }

    public String getMaxCombo() {
        return maxCombo;
    }

    public void setMaxCombo(String maxCombo) {
        this.maxCombo = maxCombo;
    }

    public Float getDifficultyrating() {
        return difficultyrating;
    }

    public void setDifficultyrating(Float difficultyrating) {
        this.difficultyrating = difficultyrating;
    }

}
