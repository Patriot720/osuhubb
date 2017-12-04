package example.cerki.osuhub.PlayerFragment.Overview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import example.cerki.osuhub.FollowersTable;
import example.cerki.osuhub.List.Player;
import example.cerki.osuhub.OsuDb;
import example.cerki.osuhub.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mUserId";

    private int mUserId;
    private String mUsername;


    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance(int userId) {
        OverviewFragment fragment = new OverviewFragment();
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
            final TextView username = view.findViewById(R.id.player_username);
            final TextView otherInfoTemp = view.findViewById(R.id.other_info_temp); // TODO CHANGE THIS
            new AvatarTask(new AvatarTask.WorkDoneListener() {
                @Override
                public void workDone(Bitmap bitmap) {
                    avatar.setImageBitmap(bitmap);
                    avatar.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }).execute("https://osu.ppy.sh/users/" + mUserId + "/card"); // TODO extract these Strings
            new PlayerInfoTask(new PlayerInfoTask.workDoneListener() {
                @Override
                public void workDone(Player player) {
                    mUsername = player.getUsername();
                    username.setText(mUsername);
                    StringBuilder builder = new StringBuilder();
                    for(String key : player.getKeySet())
                        builder.append(key).append(": ").append(player.getString(key)).append("\n");
                    otherInfoTemp.setText(builder.toString());
                }
            }).execute(mUserId);
            followButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                        table.insertOrUpdate(mUserId,mUsername);
                    else
                        table.deleteFollower(mUserId);
                }
            });
            return view;
        }

}
