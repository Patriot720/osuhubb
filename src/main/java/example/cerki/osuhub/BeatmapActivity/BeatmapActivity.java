package example.cerki.osuhub.BeatmapActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.zagum.switchicon.SwitchIconView;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Score;
import example.cerki.osuhub.Mods;
import example.cerki.osuhub.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BeatmapActivity extends AppCompatActivity implements ScoreBoardFragment.OnListFragmentInteractionListener {

    private int beatmap_id;
    private ScoreBoardFragment mScoreboard;
    private MaterialDialog dialog;

    public MaterialDialog getPopUpDialog() {
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beatmap);
        beatmap_id = getIntent().getIntExtra("beatmap_id", 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> dialog.show());
        dialog = new MaterialDialog.Builder(this)
                .title(R.string.sorting_dialog_title)
                .customView(R.layout.sorting_dialog_view, false)
                .positiveText(R.string.mods_accept)
                .onPositive((dialog1, which) -> updateScoreboard(dialog1.getCustomView()))
                .build();
        Button clearButton = dialog.getCustomView().findViewById(R.id.clear_button);
        clearButton.setOnClickListener(l -> {
            clearMods(dialog.getCustomView().findViewById(R.id.mods));
            clearUsername(dialog.getCustomView().findViewById(R.id.username));
        });
        initFragments();
    }

    private void clearMods(GridLayout modsView) {
        for (int i = 0; i < modsView.getChildCount(); i++) {
            SwitchIconView icon = (SwitchIconView) modsView.getChildAt(i);
            icon.setIconEnabled(false);
        }
    }

    private void clearUsername(EditText text) {
        text.setText("");
    }

    public ScoreBoardFragment getScoreboard() {
        return mScoreboard;
    }

    protected void updateScoreboard(View customView) {
        GridLayout mods = customView.findViewById(R.id.mods);
        EditText  textView = customView.findViewById(R.id.username);
        String text = textView.getText().toString();
        int modsIntegerValue = Mods.getModsIntegerValue(mods);
        if(modsIntegerValue == 0 && text.isEmpty()) {
            mScoreboard.initData();
            return;
        }
        mScoreboard.setUpdating(true);
        if (text.isEmpty())
            updateByMods(modsIntegerValue);
        else
            updateByUsername(text);
    }

    private void updateByUsername(String username) {
        OsuAPI.getApi().getScoresBy(beatmap_id, username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mScoreboard::updateData);
    }

    private void updateByMods(int modsIntegerValue) {
        OsuAPI.getApi().getScoresBy(beatmap_id, modsIntegerValue)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mScoreboard::updateData);
    }

    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        mScoreboard = ScoreBoardFragment.newInstance(beatmap_id);
        manager.beginTransaction()
                .replace(R.id.scoreboard_fragment, mScoreboard)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beatmap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.open_in_browser) {
            openInBrowser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openInBrowser() {
        String url = "https://osu.ppy.sh/b/" + beatmap_id; // Todo
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(Score item) {

    }
}
