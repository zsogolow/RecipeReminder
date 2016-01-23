package shire.the.great.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.Ingredient;

/**
 * DAO for the domain class Ingredient.
 *
 * Created by ZachS on 1/23/2016.
 */
public class IngredientDAO extends AbstractBaseDAO<Ingredient, RecipeDbHelper> {

    public IngredientDAO(Context context, String tblName) {
        super(context, tblName);
    }

    @Override
    public void initializeDatabase(Context context) {
        mDb = new RecipeDbHelper(context);
    }

    @Override
    public List<Ingredient> get() {
        return null;
    }

    @Override
    public Ingredient get(int id) {
        return null;
    }

    @Override
    public Ingredient insert(Ingredient entityToInsert) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.INGREDIENT_NAME, entityToInsert.getName());
        cv.put(RecipeDbHelper.INGREDIENT_POSITION, entityToInsert.getPosition());
        cv.put(RecipeDbHelper.INGREDIENT_AMOUNT, entityToInsert.getAmount());
        cv.put(RecipeDbHelper.INGREDIENT_RECIPE_FK, entityToInsert.getRecipeId());

        long id = database.insert(mTableName, null, cv);
        entityToInsert.setIngredientId(id);

        database.close();

        return entityToInsert;
    }

    @Override
    public Ingredient update(Ingredient entityToUpdate) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.INGREDIENT_NAME, entityToUpdate.getName());
        cv.put(RecipeDbHelper.INGREDIENT_POSITION, entityToUpdate.getPosition());
        cv.put(RecipeDbHelper.INGREDIENT_AMOUNT, entityToUpdate.getAmount());
        cv.put(RecipeDbHelper.INGREDIENT_RECIPE_FK, entityToUpdate.getRecipeId());

        database.update(mTableName, cv, RecipeDbHelper.INGREDIENT_ID + " = ? ",
                new String[]{String.valueOf(entityToUpdate.getIngredientId())});

        database.close();

        return entityToUpdate;
    }

    @Override
    public void delete(String whereClause, String[] whereArgs) {
        SQLiteDatabase database = mDb.getWritableDatabase();
        database.delete(mTableName, whereClause, whereArgs);
        database.close();
    }
}
