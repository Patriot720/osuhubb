package example.cerki.osuhub.ui.Activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.cerki.osuhub.Data.POJO.Score;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;

/**
 * Created by cerki on 05.01.2018.
 */
@RunWith(AndroidJUnit4.class)
public class BeatmapActivityTest {

    private BeatmapActivity beatmapActivity;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("beatmapId",1262832);
        activityRule.launchActivity(intent);
        beatmapActivity = activityRule.getActivity();
        // todo cover url
    }
    @Rule
    public ActivityTestRule<BeatmapActivity> activityRule
            = new ActivityTestRule<BeatmapActivity>(BeatmapActivity.class,true,false);

    @Test
    public void testInitData() throws Exception {
        FlexibleAdapterExtension<Score> adapter = beatmapActivity.getAdapter();
        
    }
}