package shire.the.great.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.Instruction;

/**
 * DAO for the domain class Instruction.
 *
 * Created by ZachS on 1/23/2016.
 */
public class InstructionDAO extends AbstractBaseDAO<Instruction, RecipeDbHelper> {

    public InstructionDAO(Context context, String tblName) {
        super(context, tblName);
    }

    @Override
    public void initializeDatabase(Context context) {
        mDb = new RecipeDbHelper(context);
    }

    @Override
    public List<Instruction> get() {
        return null;
    }

    @Override
    public Instruction get(int id) {
        return null;
    }

    @Override
    public Instruction insert(Instruction entityToInsert) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.INSTRUCTION_TEXT, entityToInsert.getInstruction());
        cv.put(RecipeDbHelper.INSTRUCTION_POSITION, entityToInsert.getPosition());
        cv.put(RecipeDbHelper.INSTRUCTION_RECIPE_FK, entityToInsert.getRecipeId());

        long id = database.insert(mTableName, null, cv);
        entityToInsert.setInstructionId(id);

        database.close();

        return entityToInsert;
    }

    @Override
    public Instruction update(Instruction entityToUpdate) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.INSTRUCTION_TEXT, entityToUpdate.getInstruction());
        cv.put(RecipeDbHelper.INSTRUCTION_POSITION, entityToUpdate.getPosition());
        cv.put(RecipeDbHelper.INSTRUCTION_RECIPE_FK, entityToUpdate.getRecipeId());

        database.update(mTableName, cv, RecipeDbHelper.INSTRUCTION_ID + " = ? ",
                new String[]{String.valueOf(entityToUpdate.getInstructionId())});

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
