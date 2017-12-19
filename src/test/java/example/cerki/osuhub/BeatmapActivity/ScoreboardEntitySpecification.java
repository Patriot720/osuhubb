package example.cerki.osuhub.BeatmapActivity;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Score;
import example.cerki.osuhub.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by cerki on 14.12.2017.
 */
@RunWith(RobolectricTestRunner.class)
public class ScoreboardEntitySpecification{


    private ScoreBoardFragment scoreBoard;

    @Before
    public void setUp() throws Exception {
        scoreBoard = ScoreBoardFragment.newInstance(1419986);
        startFragment(scoreBoard,BeatmapActivity.class);
    }

    @Test
    public void testApi() throws Exception {
        List<Score> scores = OsuAPI.getApi().getScoresBy(1419986).blockingGet();
        assertEquals(scores.size(),50);
    }

    @Test
    public void ShouldDisplayPerformance() throws Exception {
        RecyclerView view = (RecyclerView) scoreBoard.getView();
        FlexibleAdapter adapter = (FlexibleAdapter) view.getAdapter();
        List<Score> scores = OsuAPI.getApi().getScoresBy(1419986).blockingGet();
        adapter.updateDataSet(scores);
        Score item = (Score) adapter.getItem(0);
        assertTrue(adapter.getItemCount() == 50);
    }

    @Test
    public void ShouldDisplayBasicInfoAboutTheScore() throws Exception {
        // rank,score,accuracy, etc;
    }

    @Test
    public void ShouldOpenUserActivityOnClick() throws Exception {
        //Given user entity
        //When clicking on it
        // Then open user acitvity;
    }
}
