package example.cerki.osuhub.PlayerFragment.RecentPlays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import example.cerki.osuhub.ui.Fragments.PlayerRecentScoresFragment;

/**
 * Created by cerki on 23.12.2017.
 */
@RunWith(RobolectricTestRunner.class)
public class RecentScoresFragmentTest {
    @Test
    public void initDataTest() throws Exception {
        PlayerRecentScoresFragment fragment = PlayerRecentScoresFragment.newInstance(2831793, "filsdelama");
        fragment.initData();
    }
}