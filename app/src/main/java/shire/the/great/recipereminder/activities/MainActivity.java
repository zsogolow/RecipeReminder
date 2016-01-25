package shire.the.great.recipereminder.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import shire.the.great.domain.models.RecipeCategory;
import shire.the.great.recipereminder.R;
import shire.the.great.recipereminder.context.AppContext;
import shire.the.great.recipereminder.datasource.RecipeAccess;
import shire.the.great.recipereminder.fragments.RecipesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecipesFragment.OnFragmentInteractionListener {

    private List<RecipeCategory> mRecipeCategories;
    private SubMenu mCategoryGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes navigation drawer and toolbar
        initializeNavigationDrawer();

        // Initializes the floating action button to add recipes
        initializeFloatingAddAction();

        // initialize the AppContext for the first time
        AppContext appContext = AppContext.instance(this);

        // Get the list of RecipeCategory's to populate the menu with
        mRecipeCategories = RecipeAccess.getCategories(this);

        // Get the navigation view, this is the view in the drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Initialize the navigation view's list of categories with those
        // pulled from the database
        initializeCategories(mRecipeCategories, navigationView);

        // Add the All Recipes fragment first, this is the base fragment
        // Pressing the back button from a different fragment will return
        // the user to this fragment before exiting
        RecipesFragment recipesFragment = RecipesFragment
                .newInstance(AppContext.ALL_CATEGORIES.getName(), AppContext.ALL_CATEGORIES);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, recipesFragment)
                .commit();

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void initializeCategories(List<RecipeCategory> categories, NavigationView navigationView) {
        // The group we are adding Category menu items to
        mCategoryGroup = navigationView.getMenu().findItem(R.id.nav_category_parent).getSubMenu();

        for (RecipeCategory category : mRecipeCategories) {
            mCategoryGroup.add(R.id.nav_category_group, mRecipeCategories.indexOf(category), Menu.NONE, category.getName())
                    .setCheckable(true)
                    .setIcon(R.drawable.ic_menu_food3);
        }

        mCategoryGroup.setGroupCheckable(R.id.nav_category_group, true, true);
        mCategoryGroup.add(R.id.nav_category_group, mRecipeCategories.size(), Menu.NONE, R.string.nav_add_category)
                .setCheckable(false)
                .setIcon(R.drawable.ic_menu_add);
    }

    private void initializeFloatingAddAction() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecipeIntent = new Intent(getBaseContext(), AddRecipeActivity.class);
                startActivity(addRecipeIntent);
            }
        });
    }

    private void initializeNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the DrawerLayout and ActionBarToggle
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppContext appContext = AppContext.instance(this);

        RecipesFragment topFragment = RecipesFragment
                .newInstance(appContext.getSelectedCategory().getName(), appContext.getSelectedCategory());
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, topFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            // Pop the fragment added to the back stack, the new fragment
            // will be the All Recipes fragment
            getFragmentManager().popBackStackImmediate();
            getFragmentManager().beginTransaction().commit();

            // Get a reference to the AppContext
            AppContext appContext = AppContext.instance(this);

            // set the all recipes menu item to checked, and the last
            // selected category to unchecked and set the
            // AppContext.mSelectedCategory to All Recipes
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            navView.getMenu().getItem(0).setChecked(true);

            int categoryIndex = mRecipeCategories.indexOf(appContext.getSelectedCategory());
            mCategoryGroup.getItem(categoryIndex).setChecked(false);

            appContext.setSelectedCategory(AppContext.ALL_CATEGORIES);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        RecipeCategory selectedCategory = null;
        if (id < mRecipeCategories.size()) {
            selectedCategory = mRecipeCategories.get(id);
        }

        // remove previously add back stack entries if any exist,
        // we only want one backwards navigation on back pressed from
        // the current fragment to the base all recipes fragment
        for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {
            getFragmentManager().popBackStack();
        }

        AppContext appContext = AppContext.instance(this);

        if (id == R.id.nav_recipes_all) {
            RecipesFragment recipesFragment = RecipesFragment.newInstance("All Recipes", AppContext.ALL_CATEGORIES);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, recipesFragment)
                    .commit();
            appContext.setSelectedCategory(AppContext.ALL_CATEGORIES);
        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        } else if (id == R.id.nav_participate) {
            Intent participateIntent = new Intent(this, ParticipateActivity.class);
            startActivity(participateIntent);
        } else if (id == mRecipeCategories.size()) {
            Intent editCategoriesIntent = new Intent(this, EditCategoriesActivity.class);
            startActivity(editCategoriesIntent);
        } else {
            if (selectedCategory != null) {
                RecipesFragment recipesFragment = RecipesFragment.newInstance(selectedCategory.getName(), selectedCategory);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, recipesFragment)
                        .addToBackStack(null)
                        .commit();
                appContext.setSelectedCategory(selectedCategory);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // handle interaction between the fragments
    }
}
