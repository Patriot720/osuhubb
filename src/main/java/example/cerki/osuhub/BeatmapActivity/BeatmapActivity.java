package example.cerki.osuhub.BeatmapActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import example.cerki.osuhub.API.POJO.Score;
import example.cerki.osuhub.R;

public class BeatmapActivity extends AppCompatActivity implements ScoreBoardFragment.OnListFragmentInteractionListener{

    private int beatmap_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beatmap);
        beatmap_id = getIntent().getIntExtra("beatmap_id",0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            new MaterialDialog.Builder(this)
                    .title(R.string.sorting_dialog_title)
                    .customView(R.layout.sorting_dialog_view,false);



        });
        initFragments();
    }

    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.scoreboard_fragment,ScoreBoardFragment.newInstance(beatmap_id))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beatmap,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.open_in_browser){
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
