package example.cerki.osuhub;

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

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.utils.Log;
import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.Feed.FeedItemFragment;
import example.cerki.osuhub.List.ListFragment;
import example.cerki.osuhub.Notifications.NotificationsService;
import example.cerki.osuhub.PlayerFragment.PlayerFragment;
import example.cerki.osuhub.Searching.SearchHandler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jonathanfinerty.once.Once;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        {

    private FragmentManager mFragmentManager;
    private String SCHEDULE_NOTIFICATIONS_TAG = "tag";
    private MaterialSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            fragment = FeedItemFragment.newInstance();
        } else if (id == R.id.nav_list) {
            fragment = ListFragment.newInstance();
        }
        mFragmentManager.beginTransaction().replace(R.id.content_main,fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void scheduleNotifications() {
        if (!Once.beenDone(Once.THIS_APP_INSTALL, SCHEDULE_NOTIFICATIONS_TAG)) {
            NotificationsService.scheduleSync(this);
            Once.markDone(SCHEDULE_NOTIFICATIONS_TAG);
        }
    }
        }
