package example.cerki.osuhub.Feed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.cerki.osuhub.R;
public class FeedFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public FeedFragment() {
        // Required empty public constructor
    }
// TODO  Should have callback to activity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

}
