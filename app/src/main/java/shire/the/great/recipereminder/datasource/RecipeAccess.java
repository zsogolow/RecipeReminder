package shire.the.great.recipereminder.datasource;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shire.the.great.data.dao.FactoryDAO;
import shire.the.great.data.dao.RecipeCategoryDAO;
import shire.the.great.data.dao.RecipeDAO;
import shire.the.great.domain.models.Recipe;
import shire.the.great.domain.models.RecipeCategory;

/**
 * An access class that bridges the application logic to the
 * data access layer.
 *
 * Created by ZachS on 1/24/2016.
 */
public class RecipeAccess {

    public static Map<RecipeCategory, Recipe> getRecipesByCategory(Context context) {
        Map<RecipeCategory, Recipe> recipeMap = new HashMap<>();

        RecipeDAO recipeDAO = new FactoryDAO(context).getRecipeDAO();
        List<Recipe> recipeList = recipeDAO.get();
        for (Recipe recipe : recipeList) {
            recipeMap.put(recipe.getCategory(), recipe);
        }

        return recipeMap;
    }

    public static List<RecipeCategory> getCategories(Context context) {
        RecipeCategoryDAO recipeCategoryDAO = new FactoryDAO(context).getRecipeCategoryDAO();
        return recipeCategoryDAO.get();
    }
}
