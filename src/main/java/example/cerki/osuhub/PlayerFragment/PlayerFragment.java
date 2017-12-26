package example.cerki.osuhub.PlayerFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.cerki.osuhub.MainActivity;
import example.cerki.osuhub.PlayerFragment.Overview.OverviewFragment;
import example.cerki.osuhub.PlayerFragment.RecentPlays.RecentScoresFragment;
import example.cerki.osuhub.PlayerFragment.TopPlays.TopPlaysFragment;
import example.cerki.osuhub.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "mUsername" ;

    private int userId;
    private String mUsername;

    private OnFragmentInteractionListener mListener;

    public PlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    public static PlayerFragment newInstance(int userId,String username) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        args.putString(ARG_PARAM2,username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
            mUsername = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_player_detailed, container, false);
        TabLayout tabs = inflate.findViewById(R.id.player_tabs);
        ViewPager pager = inflate.findViewById(R.id.player_pager);
        tabs.setupWithViewPager(pager);
        setupPager(pager);
        return inflate;
    }

    private void setupPager(ViewPager pager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(OverviewFragment.newInstance(userId, mUsername),getString(R.string.player_overview));
        adapter.addFragment(RecentScoresFragment.newInstance(userId,mUsername),getString(R.string.player_recent));
        adapter.addFragment(TopPlaysFragment.newInstance(userId,mUsername),getString(R.string.player_top));  // Todo remove these two arguments userId and username;
        pager.setAdapter(adapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other mFragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
