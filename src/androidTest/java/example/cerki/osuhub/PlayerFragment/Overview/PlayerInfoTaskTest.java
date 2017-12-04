package example.cerki.osuhub.PlayerFragment.Overview;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import example.cerki.osuhub.List.Player;

import static org.junit.Assert.*;

/**
 * Created by cerki on 04-Dec-17.
 */
@RunWith(AndroidJUnit4.class)
public class PlayerInfoTaskTest {
    @Test
    public void doInBackground() throws Exception {
        PlayerInfoTask playerInfoTask = new PlayerInfoTask(new PlayerInfoTask.workDoneListener() {
            @Override
            public void workDone(Player player) {

            }
        });
        playerInfoTask.doInBackground(2831793);
    }

}