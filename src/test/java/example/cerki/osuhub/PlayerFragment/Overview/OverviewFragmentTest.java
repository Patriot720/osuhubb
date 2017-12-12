package example.cerki.osuhub.PlayerFragment.Overview;

import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.POJO.Following;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Created by cerki on 11-Dec-17.
 */
@RunWith(RobolectricTestRunner.class)
public class OverviewFragmentTest {

    private ApiDatabase db;

    @Before
    public void setUp() throws Exception {
        db = Room.databaseBuilder(RuntimeEnvironment.application,ApiDatabase.class,"test-db").allowMainThreadQueries().build();
    }

    @Test
    public void testCallable() throws Exception {
        Completable.fromAction(()->db.followingDao().insert(new Following(1, 223L,"username")))
                .subscribeOn(Schedulers.newThread())
                .subscribe(()->{
                    int id = db.followingDao().getBy(1).id;
                    assertEquals(id,1);
                });
        Thread.sleep(1000);
    }

    @Test
    public void testMaybe() throws Exception {
        db.followingDao().insert(new Following(1,223L,"username"));
        Maybe.fromCallable(()->db.followingDao().getBy(1))
                .subscribeOn(Schedulers.newThread())
                .subscribe((items)-> assertEquals(items.id,1));
        Thread.sleep(1000);
    }
}