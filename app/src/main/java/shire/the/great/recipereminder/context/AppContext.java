package shire.the.great.recipereminder.context;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Map;

import shire.the.great.domain.models.Recipe;
import shire.the.great.domain.models.RecipeCategory;
import shire.the.great.recipereminder.datasource.RecipeAccess;

/**
 * A Singleton object used to load and store some data
 * to be shared across the application.
 *
 * Created by ZachS on 1/24/2016.
 */
public class AppContext {

    public static final RecipeCategory ALL_CATEGORIES = new RecipeCategory(-1, "All Recipes");

    private static AppContext mInstance;
    private static boolean mLoaded;

    private Context mAndroidContext;
    private Map<RecipeCategory, Recipe> mCategoryRecipeMap;

    private RecipeCategory mSelectedCategory = null;

    private AppContext(Context context) {
        mAndroidContext = context;
        new LoadRecipesTask().execute();
        mSelectedCategory = ALL_CATEGORIES;
    }

    public static AppContext instance(Context context) {
        if (mInstance == null) {
            mInstance = new AppContext(context);
        }

        return mInstance;
    }

    public boolean isLoaded() {
        return mLoaded;
    }

    public Map<RecipeCategory, Recipe> getRecipeMap() {
        return mCategoryRecipeMap;
    }

    public RecipeCategory getSelectedCategory() {
        return mSelectedCategory;
    }

    public void setSelectedCategory(RecipeCategory category) {
        mSelectedCategory = category;
    }

    private class LoadRecipesTask extends AsyncTask<String, Integer, Map<RecipeCategory, Recipe>> {
        protected Map<RecipeCategory, Recipe> doInBackground(String... urls) {
            mLoaded = false;
            return RecipeAccess.getRecipesByCategory(mAndroidContext);
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Map<RecipeCategory, Recipe> result) {
            mCategoryRecipeMap = result;
            mLoaded = true;
        }
    }
}
