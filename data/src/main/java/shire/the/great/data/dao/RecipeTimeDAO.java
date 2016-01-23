package shire.the.great.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.RecipeTime;

/**
 * DAO for the domain class RecipeTime.
 *
 * Created by ZachS on 1/23/2016.
 */
public class RecipeTimeDAO extends AbstractBaseDAO<RecipeTime, RecipeDbHelper> {

    public RecipeTimeDAO(Context context, String tblName) {
        super(context, tblName);
    }

    @Override
    public void initializeDatabase(Context context) {
        mDb = new RecipeDbHelper(context);
    }

    @Override
    public List<RecipeTime> get() {
        return null;
    }

    @Override
    public RecipeTime get(int id) {
        return null;
    }

    @Override
    public RecipeTime insert(RecipeTime entityToInsert) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.RECIPE_COOK_DURATION, entityToInsert.getDuration());
        cv.put(RecipeDbHelper.RECIPE_TIME_ID_FK, entityToInsert.getRecipeId());

        long id = database.insert(mTableName, null, cv);
        entityToInsert.setRecipeTimeId(id);

        database.close();

        return entityToInsert;
    }

    @Override
    public RecipeTime update(RecipeTime entityToUpdate) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.RECIPE_COOK_DURATION, entityToUpdate.getDuration());
        cv.put(RecipeDbHelper.RECIPE_TIME_ID_FK, entityToUpdate.getRecipeId());

        database.update(mTableName, cv, RecipeDbHelper.RECIPE_TIME_ID + " = ? ",
                new String[]{String.valueOf(entityToUpdate.getRecipeTimeId())});
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
