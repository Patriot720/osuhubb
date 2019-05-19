package example.cerki.osuhub.ui.Activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.cerki.osuhub.Data.POJO.Score;
import example.cerki.osuhub.ui.Dialogs.ScoreboardSortingDialog;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;
import example.cerki.osuhub.ui.SwitchIconExtend;
import example.cerki.osuhub.ui.ViewWrappers.ScoreboardViewWrap;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cerki on 05.01.2018.
 */
@RunWith(AndroidJUnit4.class)
public class BeatmapActivityTest {

    private BeatmapActivity beatmapActivity;
    private ScoreboardSortingDialog scoreboardSortingDialog;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("beatmapId",1262832);
        activityRule.launchActivity(intent);
        beatmapActivity = activityRule.getActivity();
        scoreboardSortingDialog = beatmapActivity.getDialog();

        // todo cover url
    }
    @Rule
    public ActivityTestRule<BeatmapActivity> activityRule
            = new ActivityTestRule<BeatmapActivity>(BeatmapActivity.class,true,false);

    @Test
    public void testInitData() throws Exception {
        FlexibleAdapterExtension<Score> adapter = beatmapActivity.getAdapter();
        ScoreboardViewWrap scoreboardViewWrap = beatmapActivity.getScoreboardViewWrap();
        Thread.sleep(1000);
        assertTrue(adapter.getItemCount() > 0);
        assertEquals(adapter.getItemCount(),50);
        assertTrue(scoreboardViewWrap.getRecycler().getChildCount() > 0);
        assertEquals(scoreboardViewWrap.getProgressBar().getVisibility(), View.GONE);
    }

    @Test
    public void testSortingDialogIntegerValue() throws Exception {
        int modsIntegerValue;
        SwitchIconExtend icon = (SwitchIconExtend) scoreboardSortingDialog.getModsView().getChildAt(2);
        modsIntegerValue = scoreboardSortingDialog.getModsIntegerValue();
        assertEquals(modsIntegerValue,0);
        icon.switchState();
        modsIntegerValue = scoreboardSortingDialog.getModsIntegerValue();
        assertTrue(modsIntegerValue > 0);
    }

    @Test
    public void testSorting() throws Exception {
        scoreboardSortingDialog.setUsername("Cookiezi");
        Thread.sleep(1000);
        assertEquals(beatmapActivity.getAdapter().getItemCount(),50);
        beatmapActivity.updateScoreboard();
        Thread.sleep(1000);
        assertEquals(beatmapActivity.getAdapter().getItemCount(),1);
    }
}