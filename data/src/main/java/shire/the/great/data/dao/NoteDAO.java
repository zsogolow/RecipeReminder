package shire.the.great.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.Note;

/**
 * DAO for the domain class Note.
 *
 * Created by ZachS on 1/23/2016.
 */
public class NoteDAO extends AbstractBaseDAO<Note, RecipeDbHelper> {

    public NoteDAO(Context context, String tblName) {
        super(context, tblName);
    }

    @Override
    public void initializeDatabase(Context context) {
        mDb = new RecipeDbHelper(context);
    }

    @Override
    public List<Note> get() {
        return null;
    }

    @Override
    public Note get(int id) {
        return null;
    }

    @Override
    public Note insert(Note entityToInsert) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.NOTE_TEXT, entityToInsert.getNote());
        cv.put(RecipeDbHelper.NOTE_ENTITY_FK, entityToInsert.getEntityId());

        long id = database.insert(mTableName, null, cv);
        entityToInsert.setEntityId(id);

        database.close();

        return entityToInsert;
    }

    @Override
    public Note update(Note entityToUpdate) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.NOTE_TEXT, entityToUpdate.getNote());
        cv.put(RecipeDbHelper.NOTE_ENTITY_FK, entityToUpdate.getEntityId());

        database.update(mTableName, cv, RecipeDbHelper.NOTE_ID + " = ? ",
                new String[]{String.valueOf(entityToUpdate.getNoteId())});

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
