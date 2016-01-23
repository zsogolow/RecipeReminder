package shire.the.great.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.RecipeCategory;

/**
 * DAO for the domain class RecipeCategory.
 *
 * Created by ZachS on 1/23/2016.
 */
public class RecipeCategoryDAO extends AbstractBaseDAO<RecipeCategory, RecipeDbHelper> {

    public RecipeCategoryDAO(Context context, String tblName) {
        super(context, tblName);
    }

    @Override
    public void initializeDatabase(Context context) {
        mDb = new RecipeDbHelper(context);
    }

    @Override
    public List<RecipeCategory> get() {
        return null;
    }

    @Override
    public RecipeCategory get(int id) {
        return null;
    }

    @Override
    public RecipeCategory insert(RecipeCategory entityToInsert) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.RECIPE_CATEGORY, entityToInsert.getName());

        long id = database.insert(mTableName, null, cv);
        entityToInsert.setRecipeCategoryId(id);

        database.close();

        return entityToInsert;
    }

    @Override
    public RecipeCategory update(RecipeCategory entityToUpdate) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.RECIPE_CATEGORY, entityToUpdate.getName());

        database.update(mTableName, cv, RecipeDbHelper.RECIPE_CATEGORY_ID + " = ? ",
                new String[]{String.valueOf(entityToUpdate.getCategoryId())});

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
