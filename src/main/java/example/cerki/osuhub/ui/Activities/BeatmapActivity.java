package example.cerki.osuhub.ui.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.Score;
import example.cerki.osuhub.R;
import example.cerki.osuhub.ui.Dialogs.ScoreboardSortingDialog;
import example.cerki.osuhub.ui.FlexibleAdapterExtension;
import example.cerki.osuhub.ui.ViewWrappers.ScoreboardViewWrap;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BeatmapActivity extends AppCompatActivity{

    private int beatmapId;
    private ScoreboardSortingDialog dialog;
    private FlexibleAdapterExtension<Score> adapter;
    private ScoreboardViewWrap scoreboardViewWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beatmap);
        beatmapId = getIntent().getIntExtra("beatmapId", 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> dialog.show());

        dialog = new ScoreboardSortingDialog(this,this::updateScoreboard);

        initScoreboard();
    }



    protected void updateScoreboard() {
        int modsIntegerValue = dialog.getModsIntegerValue();
        String username = dialog.getUsername();
        if(dialog.isEmpty()) {
            initData();
            return;
        }
        if (dialog.isUsernameEmpty())
            updateByMods(modsIntegerValue);
        else
            updateByUsername(username);
    }

    private void initData() {
        OsuAPI.getApi().getScoresBy(beatmapId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)-> adapter.updateDataSet(items));
    }

    private void updateByUsername(String username) {
        OsuAPI.getApi().getScoresBy(beatmapId, username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)->adapter.updateDataSet(items));
    }

    private void updateByMods(int modsIntegerValue) {
        OsuAPI.getApi().getScoresBy(beatmapId, modsIntegerValue)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)->adapter.updateDataSet(items));
    }

    private void initScoreboard(){
        adapter = new FlexibleAdapterExtension<Score>(null,this); // todo  ¯\_(ツ)_/¯
        scoreboardViewWrap = new ScoreboardViewWrap(findViewById(R.id.scoreboard_frame), adapter);
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
        String url = "https://osu.ppy.sh/b/" + beatmapId; // Todo
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
