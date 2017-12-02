package example.cerki.osuhub.PlayerFragment;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by cerki on 02-Dec-17.
 */
public class PlayerPagerAdapterTest {
    private PlayerPagerAdapter mAdapter;

    @Before
    public void setUp() throws Exception {
        FragmentManager mock = mock(FragmentManager.class);
        mAdapter = new PlayerPagerAdapter(mock);
    }

    @Test
    public void getItem() throws Exception {
        Fragment fragment = mock(Fragment.class);
        String title = "title";
        mAdapter.addFragment(fragment,title);
        Fragment item = mAdapter.getItem(0);
        String result_title = (String) mAdapter.getPageTitle(0);
        assertNotNull(item);
        assertEquals(title,result_title);
    }
}