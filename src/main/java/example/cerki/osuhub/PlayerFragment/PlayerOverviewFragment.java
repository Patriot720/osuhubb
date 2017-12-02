package example.cerki.osuhub.PlayerFragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerOverviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mUserId";

    // TODO: Rename and change types of parameters
    private int mUserId;


    public PlayerOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment PlayerOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerOverviewFragment newInstance(int userId) {
        PlayerOverviewFragment fragment = new PlayerOverviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_PARAM1);
        }
    }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_player_overview, container, false);
            final FollowersTable table = new FollowersTable(
                    new OsuDb(getContext()).getWritableDatabase()
            );
            ToggleButton followButton = view.findViewById(R.id.follow_button);
            if(!table.getTimestamp(mUserId).isEmpty())
                followButton.setChecked(true);
            final ImageView avatar = view.findViewById(R.id.player_avatar);
            final ProgressBar progressBar = view.findViewById(R.id.avatar_progressbar);
            new OverViewAvatarTask(new OverViewAvatarTask.WorkDoneListener() {
                @Override
                public void workDone(Bitmap bitmap) {
                    avatar.setImageBitmap(bitmap);
                    avatar.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }).execute("https://osu.ppy.sh/users/" + mUserId + "/card");
            // TODO EXTRACT STRING
            followButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        table.insertOrUpdateFollower(mUserId);
                    else
                        table.deleteFollower(mUserId);
                }
            });
            return view;
        }

}
