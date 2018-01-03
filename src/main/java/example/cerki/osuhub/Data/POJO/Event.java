package example.cerki.osuhub.Data.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cerki on 10-Dec-17.
 */

public class Event {

        @SerializedName("display_html")
        @Expose
        private String displayHtml;
        @SerializedName("beatmap_id")
        @Expose
        private String beatmapId;
        @SerializedName("beatmapset_id")
        @Expose
        private String beatmapsetId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("epicfactor")
        @Expose
        private String epicfactor;

        public String getDisplayHtml() {
            return displayHtml;
        }

        public void setDisplayHtml(String displayHtml) {
            this.displayHtml = displayHtml;
        }

        public String getBeatmapId() {
            return beatmapId;
        }

        public void setBeatmapId(String beatmapId) {
            this.beatmapId = beatmapId;
        }

        public String getBeatmapsetId() {
            return beatmapsetId;
        }

        public void setBeatmapsetId(String beatmapsetId) {
            this.beatmapsetId = beatmapsetId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEpicfactor() {
            return epicfactor;
        }

        public void setEpicfactor(String epicfactor) {
            this.epicfactor = epicfactor;
        }

    }
