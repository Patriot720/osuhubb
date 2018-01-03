package example.cerki.osuhub.Feed;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import example.cerki.osuhub.Data.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.Data.Api.OsuAPI;
import example.cerki.osuhub.Data.Api.OsuApiService;

/**
 * Created by cerki on 11-Dec-17.
 */
@RunWith(RobolectricTestRunner.class)
@Ignore
public class NewFeedTaskNetworkTest {
    private ApiDatabase db;
    private OsuApiService api;

    @Before
    public void setUp() throws Exception {
        db = ApiDatabase.createInstance(RuntimeEnvironment.application);
        api = OsuAPI.getApi();
    }

}