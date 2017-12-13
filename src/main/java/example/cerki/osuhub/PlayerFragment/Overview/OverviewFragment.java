package example.cerki.osuhub.PlayerFragment.Overview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.reflect.Field;
import java.util.Date;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.ApiDatabase.FollowingDao;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Following;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mUserId";
    private static final String ARG_PARAM2 = "mUsername";

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
    public static OverviewFragment newInstance(int userId, String username) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        args.putString(ARG_PARAM2, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_PARAM1);
            mUsername = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_overview, container, false);
        ToggleButton followButton = view.findViewById(R.id.follow_button);
        loadButtonState(followButton);
        final TextView username = view.findViewById(R.id.player_username);
        username.setText(mUsername);
        final TextView userView = view.findViewById(R.id.other_info_temp); // TODO CHANGE THIS
        loadAvatarInto(view);
        loadUserInto(userView);
        setListenersFor(followButton);
        return view;
    }

    private void setListenersFor(ToggleButton followButton) {
        followButton.setOnCheckedChangeListener((compoundButton, b) -> {
            Following following = new Following(mUserId, new Date().getTime(), mUsername);
            FollowingDao followingDb = ApiDatabase.getInstance().followingDao();
            if (b)
                followingDb.insert(following);
            else
                followingDb.delete(following);
        });
    }

    private void loadButtonState(ToggleButton followButton) {
        if (ApiDatabase.getInstance().followingDao().getBy(mUserId) != null) // Todo refactor this
            followButton.setChecked(true);
    }

    private void loadUserInto(TextView otherInfoTemp) {
        if(Util.hasActiveInternetConnection(getContext()))
        OsuAPI.getApi().getUserBy(mUserId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((user) -> {
                    StringBuilder builder = new StringBuilder();
                    Field[] fields = user.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        String val = String.valueOf(field.get(user));
                        String name = field.getName();
                        builder.append(name).append(": ").append(val).append("\n");
                    }
                    otherInfoTemp.setText(builder.toString());
                });
    }

    private void loadAvatarInto(View view) {
        final ImageView avatar = view.findViewById(R.id.player_avatar);
        final ProgressBar progressBar = view.findViewById(R.id.avatar_progressbar);
        new AvatarTask(bitmap -> {
            avatar.setImageBitmap(bitmap);
            avatar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }).execute("https://osu.ppy.sh/users/" + mUserId + "/card"); // TODO extract these Strings
    }


}
