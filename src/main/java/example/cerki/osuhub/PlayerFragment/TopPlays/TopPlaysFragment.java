package example.cerki.osuhub.PlayerFragment.TopPlays;

import android.os.Bundle;

import java.util.Collections;
import java.util.List;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.Feed.FeedItem;
import example.cerki.osuhub.MainActivity;
import example.cerki.osuhub.PlayerFragment.RecentPlays.RecentScoresFragment;
import example.cerki.osuhub.PlayerFragment.RecentPlays.RecentScoresTask;
import example.cerki.osuhub.R;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cerki on 26.12.2017.
 */

@SuppressWarnings("unchecked")
public class TopPlaysFragment extends PagerRecyclerFragment<FeedItem> {
    @Override
    public boolean setListener(MainActivity context) {
        setListener(context::feedFragmentListener);
        return true;
    }
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }
    private static final String ARG_USER_ID = "user_id";
    private static final java.lang.String ARG_USERNAME = "username";
    private int mUserId = 0;
    private String mUsername;
    @SuppressWarnings("unused")
    public static TopPlaysFragment newInstance(int userId, String username) {
        TopPlaysFragment fragment = new TopPlaysFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_USER_ID);
            mUsername = getArguments().getString(ARG_USERNAME);
        }
    }

    @Override
    public void initDataDatabase() {
        OsuAPI.getApi().getBestScoresBy(mUserId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(items -> {
                    new RecentScoresTask(this::onUpdate,items).execute(0);
                    setItems(items);
                });
    }
    public void sortItemsByDate(){
        List<BestScore> items = getItems();
        Collections.sort(items, (bestScore, t1) -> bestScore.getDate().compareTo(t1.getDate()));
        setItems(items);
    }

    @Override
    public void updateData() {
        initDataDatabase();
    }
}
