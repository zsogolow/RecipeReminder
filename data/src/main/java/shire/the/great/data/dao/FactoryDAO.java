package shire.the.great.data.dao;

import android.content.Context;

import shire.the.great.data.db.RecipeDbHelper;

/**
 * A Factory class to easily query DAO's from.
 *
 * Created by ZachS on 1/24/2016.
 */
public class FactoryDAO {

    private Context mContext;

    public FactoryDAO(Context context) {
        mContext = context;
    }

    public RecipeDAO getRecipeDAO() {
        return new RecipeDAO(mContext, RecipeDbHelper.RECIPE_TABLE);
    }

    public RecipeCategoryDAO getRecipeCategoryDAO() {
        return new RecipeCategoryDAO(mContext, RecipeDbHelper.RECIPE_CATEGORY_TABLE);
    }
}
