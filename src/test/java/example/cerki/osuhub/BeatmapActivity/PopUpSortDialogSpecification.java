package example.cerki.osuhub.BeatmapActivity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.zagum.switchicon.SwitchIconView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Score;
import example.cerki.osuhub.Mods;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.Activities.BeatmapActivity;

import static example.cerki.osuhub.R.id.clear_button;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by cerki on 21.12.2017.
 */

@RunWith(RobolectricTestRunner.class)
public class PopUpSortDialogSpecification {
    private MaterialDialog dialog;
    private ConstraintLayout dialogView;
    private BeatmapActivity beatmapActivity;
    private GridLayout modsView;

    @Before
    public void setUp() throws Exception {
        beatmapActivity = Robolectric.setupActivity(BeatmapActivity.class);
        dialog = beatmapActivity.getPopUpDialog();
        dialogView = (ConstraintLayout) dialog.getCustomView();
        modsView = dialogView.findViewById(R.id.mods);
    }

    @Test
    public void ShouldDisplayMods() throws Exception {
        GridLayout mods = dialogView.findViewById(R.id.mods);
        View mod = mods.getChildAt(0);
        assertTrue(mod instanceof SwitchIconView);
    }

    @Test
    public void ShouldDisplayPlayerScoreOnQuery() throws Exception {
        List<Score> cookiezi = OsuAPI.getApi().getScoresBy(1262832, "Rafis").blockingGet();
        assertEquals(cookiezi.size(),1);
    }

    @Test
    public void ShouldDisplayUsernameField() throws Exception {
        View viewById = dialogView.findViewById(R.id.username);
        assertTrue(viewById instanceof EditText);
    }

    @Test
    public void ShouldParseModsOnSubmit() throws Exception {
        GridLayout view = (GridLayout) dialog.findViewById(R.id.mods);
        int modsIntegerValue = Mods.getModsIntegerValue(view); // SRP violation
        assertEquals(modsIntegerValue, 0);
    }

    @Test
    public void ShouldChangeModStateOnModClick() throws Exception {
        GridLayout viewById = dialogView.findViewById(R.id.mods);
        int childCount = dialogView.getChildCount();
        for (int i = 0; i < childCount; i++) {
        SwitchIconView icon = (SwitchIconView) viewById.getChildAt(i);
        icon.performClick();
        assertTrue(icon.isIconEnabled());
        }
    }

    @Test
    public void ShouldReloadScoreboardFragmentOnSubmit() throws Exception {
        setRecyclerVisible();
        beatmapActivity.updateScoreboard(dialogView);
        assertTrue(beatmapActivity.getScoreboard().isUpdating());
    }

    @Test
    public void ShouldNotShowNomodIfNoModsSelected() throws Exception {
        setRecyclerVisible();
        beatmapActivity.updateScoreboard(dialogView);
        assertFalse(beatmapActivity.getScoreboard().isUpdating());
    }

    private void setRecyclerVisible() {
        RecyclerView recyclerView = beatmapActivity.getScoreboard().getView().findViewById(R.id.list);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Test
    public void ShouldClearModsOnClearButtonClick() throws Exception {
        SwitchIconView icon = (SwitchIconView) modsView.getChildAt(0);
        icon.switchState();
        Button clearButton = dialogView.findViewById(clear_button);
        clearButton.performClick();
        assertEquals(Mods.getModsIntegerValue(modsView),0);
    }

    @Test
    public void ShouldClearUsernameOnClearButtonClick() throws Exception {
        EditText text = dialogView.findViewById(R.id.username);
        String wtf = "wtf";
        text.setText(wtf);
        Button clearButton = dialogView.findViewById(clear_button);
        clearButton.performClick();
        assertEquals(text.getText().toString(),"");
    }

    @Test
    public void ShouldReloadScoreboardIfNoMods() throws Exception {
        FlexibleAdapter<Score> scoreFlexibleAdapter = beatmapActivity.getScoreboard().getmAdapter();

    }
}
