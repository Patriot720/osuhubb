package example.cerki.osuhub;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.IOException;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.utils.Log;
import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.BeatmapActivity.BeatmapActivity;
import example.cerki.osuhub.Feed.FeedItem;
import example.cerki.osuhub.Feed.FeedItemFragment;
import example.cerki.osuhub.List.ListFragment;
import example.cerki.osuhub.Notifications.NotificationsService;
import example.cerki.osuhub.PlayerFragment.PlayerFragment;
import example.cerki.osuhub.Searching.SearchHandler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jonathanfinerty.once.Once;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PlayerFragment.OnFragmentInteractionListener,
        ListFragment.OnListFragmentInteractionListener,
        FeedItemFragment.OnListFragmentInteractionListener{

    private FragmentManager mFragmentManager;
    private String SCHEDULE_NOTIFICATIONS_TAG = "tag";
    private MaterialSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_LOGS"
        ,"android.permission.READ_EXTERNAL_STORAGE"
        ,"android.permission.INTERNET"};

        int permsRequestCode = 200;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }
                    Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(
                    Environment.getExternalStorageDirectory().getPath())); // Todo refactor
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();

        ApiDatabase.createInstance(this);


        FlexibleAdapter.enableLogs(Log.Level.VERBOSE);

        setupSearchView();

        Once.initialise(this);
        scheduleNotifications();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else if(mSearchView.isSearchOpen()){
            mSearchView.closeSearch();
        }
         else if(backStackEntryCount > 0)
                mFragmentManager.popBackStack();
        else
            super.onBackPressed();
    }

    private void setupSearchView(){
        mSearchView = findViewById(R.id.search_view);
        SearchHandler searchHandler = new SearchHandler();
        mSearchView.setSubmitOnClick(true);
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                User userBy;
                userBy = ApiDatabase.getInstance().userDao().getUserBy(query);
                if(userBy == null){
                    OsuAPI.getApi().getUserBy(query)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((user)->{ // Todo refactor
                                if(user.size() == 0)
                                    return;
                        launchUserFragment(user.get(0));
                    });
                }
                else
                    launchUserFragment(userBy);
                mSearchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchHandler.doMySearch(newText,mSearchView);
                return true;
            }
        });
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendLoagcatMail();
    }

    public void SendLoagcatMail(){

        // save logcat in file
        File outputFile = new File(Environment.getExternalStorageDirectory(),
                "logcat.txt");
        try {
            Runtime.getRuntime().exec(
                    "logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //send file using email
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // Set type to "email"
        emailIntent.setType("vnd.android.cursor.dir/email");
        String to[] = {"cerkin-3@yandex.ru"};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, outputFile.getAbsolutePath());
        // the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));
    }

    private void launchUserFragment(User user) {
           getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("stack")
                            .replace(R.id.content_main,PlayerFragment.newInstance(user.getUserId(),user.getUsername()))
                            .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;
        if (id == R.id.nav_feed) {
            FeedItemFragment feedItemFragment = new FeedItemFragment();
            feedItemFragment.setListener(this::feedFragmentListener);
            fragment = feedItemFragment;
        } else if (id == R.id.nav_list) {
            fragment = ListFragment.newInstance();
        }
        mFragmentManager.beginTransaction().replace(R.id.content_main,fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(IFlexible player) {
        if(player instanceof User) {
            User user = ((User) player);
            android.support.v4.app.Fragment fragment = PlayerFragment.newInstance(user.getUserId(), user.getUsername());
            mFragmentManager.beginTransaction().add(R.id.content_main, fragment)
                    .addToBackStack("stack")
                    .commit();
        }
    }
    private void scheduleNotifications() {
        if (!Once.beenDone(Once.THIS_APP_INSTALL, SCHEDULE_NOTIFICATIONS_TAG)) {
            NotificationsService.scheduleSync(this);
            Once.markDone(SCHEDULE_NOTIFICATIONS_TAG);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void feedFragmentListener(IFlexible item) {
            // Todo implement Map Activity
            FeedItem realItem = (FeedItem) item;
            Intent intent = new Intent(this, BeatmapActivity.class);
            intent.putExtra("beatmap_id",realItem.beatmap_id);
            startActivity(intent);
        }
}
