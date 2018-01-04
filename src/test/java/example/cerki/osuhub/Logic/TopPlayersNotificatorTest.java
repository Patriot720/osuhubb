package example.cerki.osuhub.Logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cerki on 03.01.2018.
 */
public class TopPlayersNotificatorTest {

    private TopPlayersNotificator topPlayersNotificator;

    @Before
    public void setUp() throws Exception {
        topPlayersNotificator = new TopPlayersNotificator();
    }

    @Test
    public void checkForNewScores() throws Exception {
        topPlayersNotificator.getPlayersWithNewScores();
    }
}