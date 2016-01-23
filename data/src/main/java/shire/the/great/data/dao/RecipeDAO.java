package shire.the.great.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.Ingredient;
import shire.the.great.domain.models.Instruction;
import shire.the.great.domain.models.Note;
import shire.the.great.domain.models.Recipe;
import shire.the.great.domain.models.RecipeCategory;
import shire.the.great.domain.models.RecipeTime;

/**
 * DAO for the domain class Recipe.
 *
 * Created by ZachS on 1/21/2016.
 */
public class RecipeDAO extends AbstractBaseDAO<Recipe, RecipeDbHelper> {

    public RecipeDAO(Context context, String tblName) {
        super(context, tblName);
    }

    @Override
    public void initializeDatabase(Context context) {
        mDb = new RecipeDbHelper(context);
    }

    @Override
    public List<Recipe> get() {
        // Used to retrieve Recipe objects quickly when
        // updating their foreign key relationships
        Map<Integer, Recipe> recipeIdMap = new HashMap<>();

        SQLiteDatabase database = mDb.getReadableDatabase();

        String sqlSelectJoinString =
                "SELECT * FROM %s "
                        + "%s JOIN %s "
                        + "ON %s.%s = %s.%s";

        @SuppressWarnings("unused")
        String sqlSelectAverage =
                "SELECT AVG(%s) FROM %s "
                        + "%s JOIN %s "
                        + "ON %s.%s = %s.%s";

        @SuppressWarnings("unused")
        String sqlSelectJoinStringWhere =
                "SELECT * FROM %s "
                        + "%s JOIN %s "
                        + "ON %s.%s = %s.%s"
                        + " WHERE %.% = ?";

        // Raw Query:
        // SELECT * FROM Recipe_tbl Recipe_tbl JOIN RecipeCategory_tbl ON Recipe_tbl.RecipeCategoryFK_col = RecipeCategory_tbl.RecipeCategoryId_col
        String sqlSelectRecipeJoinCategory = String.format(sqlSelectJoinString,
                RecipeDbHelper.RECIPE_TABLE,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_CATEGORY_TABLE,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_CATEGORY_FK,
                RecipeDbHelper.RECIPE_CATEGORY_TABLE, RecipeDbHelper.RECIPE_CATEGORY_ID);

        // Raw Query:
        // SELECT * FROM Recipe_tbl Recipe_tbl JOIN Instruction_tbl ON Instruction_tbl.RecipeFK_col = Recipe_tbl.RecipeId_col
        String sqlSelectRecipeJoinInstruct = String.format(sqlSelectJoinString,
                RecipeDbHelper.RECIPE_TABLE,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.INSTRUCTION_TABLE,
                RecipeDbHelper.INSTRUCTION_TABLE, RecipeDbHelper.INSTRUCTION_RECIPE_FK,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_ID);

        // Raw Query:
        // SELECT * FROM Recipe_tbl Recipe_tbl JOIN Ingredient_tbl ON Ingredient_tbl.RecipeFK_col = Recipe_tbl.RecipeId_col
        String sqlSelectRecipeJoinIngredients = String.format(sqlSelectJoinString,
                RecipeDbHelper.RECIPE_TABLE,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.INGREDIENT_TABLE,
                RecipeDbHelper.INGREDIENT_TABLE, RecipeDbHelper.INGREDIENT_RECIPE_FK,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_ID);

        // Raw Query:
        // SELECT * FROM Recipe_tbl Recipe_tbl JOIN Note_tbl ON Note_tbl.NoteEntityFK_col = Recipe_tbl.RecipeId_col
        String sqlSelectRecipeJoinNotes = String.format(sqlSelectJoinString,
                RecipeDbHelper.RECIPE_TABLE,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.NOTE_TABLE,
                RecipeDbHelper.NOTE_TABLE, RecipeDbHelper.NOTE_ENTITY_FK,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_ID);

        // Raw Query:
        // SELECT * FROM Recipe_tbl Recipe_tbl JOIN RecipeTime_tbl ON RecipeTime_tbl.RecipeIdFK_col = Recipe_tbl.RecipeId_col
        String sqlSelectRecipeJoinTime = String.format(sqlSelectJoinString,
                RecipeDbHelper.RECIPE_TABLE,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_TIME_TABLE,
                RecipeDbHelper.RECIPE_TIME_TABLE, RecipeDbHelper.RECIPE_TIME_ID_FK,
                RecipeDbHelper.RECIPE_TABLE, RecipeDbHelper.RECIPE_ID);


        // Begin executing the queries prepared above
        // Each query is handled like so

        // Get Cursor
        // DO
        //   process the current record
        // WHILE (Cursor has more records)
        // Close Cursor

        // Gets the initial list of recipes and their associated category's.
        // Add all of the newly created recipes to the recipeMap for quick access
        // to update these recipes later.
        Cursor recipeCategoryCursor = database.rawQuery(sqlSelectRecipeJoinCategory, null);
        if (recipeCategoryCursor.moveToFirst()) {
            do {
                int recipeId = recipeCategoryCursor
                        .getInt(recipeCategoryCursor.getColumnIndex(RecipeDbHelper.RECIPE_ID));
                String recipeName = recipeCategoryCursor
                        .getString(recipeCategoryCursor.getColumnIndex(RecipeDbHelper.RECIPE_NAME));
                String recipePhoto = recipeCategoryCursor
                        .getString(recipeCategoryCursor.getColumnIndex(RecipeDbHelper.RECIPE_PHOTO));
                String uploadedBy = recipeCategoryCursor
                        .getString(recipeCategoryCursor.getColumnIndex(RecipeDbHelper.RECIPE_UPLOADED_BY));
                int recipeCategoryFK = recipeCategoryCursor
                        .getInt(recipeCategoryCursor.getColumnIndex(RecipeDbHelper.RECIPE_CATEGORY_FK));
                String recipeCategory = recipeCategoryCursor
                        .getString(recipeCategoryCursor.getColumnIndex(RecipeDbHelper.RECIPE_CATEGORY));
                RecipeCategory category = new RecipeCategory(recipeCategoryFK, recipeCategory);
                Recipe recipe = new Recipe(recipeId, recipeName, recipePhoto, uploadedBy, category);

                recipeIdMap.put(recipeId, recipe);
            } while (recipeCategoryCursor.moveToNext());
        }
        recipeCategoryCursor.close();

        // Gets all of the associated times recorded for each recipe
        Cursor recipeTimeCursor = database.rawQuery(sqlSelectRecipeJoinTime, null);
        if (recipeTimeCursor.moveToFirst()) {
            do {
                int recipeId = recipeTimeCursor
                        .getInt(recipeTimeCursor.getColumnIndex(RecipeDbHelper.RECIPE_ID));
                int recipeTimeId = recipeTimeCursor
                        .getInt(recipeTimeCursor.getColumnIndex(RecipeDbHelper.RECIPE_TIME_ID));
                int duration = recipeTimeCursor
                        .getInt(recipeTimeCursor.getColumnIndex(RecipeDbHelper.RECIPE_COOK_DURATION));
                RecipeTime recipeTime = new RecipeTime(recipeTimeId, duration, recipeId);

                recipeIdMap.get(recipeId).addTime(recipeTime);
            } while (recipeTimeCursor.moveToNext());
        }
        recipeTimeCursor.close();

        // Gets all of the instructions for each recipe
        Cursor recipeInstructCursor = database.rawQuery(sqlSelectRecipeJoinInstruct, null);
        if (recipeInstructCursor.moveToFirst()) {
            do {
                int recipeId = recipeInstructCursor
                        .getInt(recipeInstructCursor.getColumnIndex(RecipeDbHelper.RECIPE_ID));
                int instructionId = recipeInstructCursor
                        .getInt(recipeInstructCursor.getColumnIndex(RecipeDbHelper.INSTRUCTION_ID));
                String instructionText = recipeInstructCursor
                        .getString(recipeInstructCursor.getColumnIndex(RecipeDbHelper.INSTRUCTION_TEXT));
                int instructPosition = recipeInstructCursor
                        .getInt(recipeInstructCursor.getColumnIndex(RecipeDbHelper.INSTRUCTION_POSITION));
                Instruction instruction = new Instruction(instructionId, instructionText, instructPosition, recipeId);

                recipeIdMap.get(recipeId).addInstruction(instruction);
            } while (recipeInstructCursor.moveToNext());
        }
        recipeInstructCursor.close();

        // Gets all of the ingredients for each recipe
        Cursor recipeIngredCursor = database.rawQuery(sqlSelectRecipeJoinIngredients, null);
        if (recipeIngredCursor.moveToFirst()) {
            do {
                int recipeId = recipeIngredCursor
                        .getInt(recipeIngredCursor.getColumnIndex(RecipeDbHelper.RECIPE_ID));
                int ingredientId = recipeIngredCursor
                        .getInt(recipeIngredCursor.getColumnIndex(RecipeDbHelper.INGREDIENT_ID));
                String ingredientName = recipeIngredCursor
                        .getString(recipeIngredCursor.getColumnIndex(RecipeDbHelper.INGREDIENT_NAME));
                String ingredientAmount = recipeIngredCursor
                        .getString(recipeIngredCursor.getColumnIndex(RecipeDbHelper.INGREDIENT_AMOUNT));
                int ingredientPosition = recipeIngredCursor
                        .getInt(recipeIngredCursor.getColumnIndex(RecipeDbHelper.INSTRUCTION_POSITION));
                Ingredient ingredient = new Ingredient(ingredientId, ingredientName, ingredientAmount, ingredientPosition, recipeId);

                recipeIdMap.get(recipeId).addIngredient(ingredient);
            } while (recipeIngredCursor.moveToNext());
        }
        recipeIngredCursor.close();

        // Gets all of the notes for each recipe
        Cursor recipeNoteCursor = database.rawQuery(sqlSelectRecipeJoinNotes, null);
        if (recipeNoteCursor.moveToFirst()) {
            do {
                int recipeId = recipeNoteCursor
                        .getInt(recipeNoteCursor.getColumnIndex(RecipeDbHelper.RECIPE_ID));
                int noteId = recipeNoteCursor
                        .getInt(recipeNoteCursor.getColumnIndex(RecipeDbHelper.NOTE_ID));
                String noteText = recipeNoteCursor
                        .getString(recipeNoteCursor.getColumnIndex(RecipeDbHelper.NOTE_TEXT));
                Note note = new Note(noteId, noteText, recipeId);

                recipeIdMap.get(recipeId).addNote(note);
            } while (recipeNoteCursor.moveToNext());
        }
        recipeNoteCursor.close();

        // construct a list to return
        List<Recipe> recipes = new ArrayList<>();

        for (int id : recipeIdMap.keySet()) {
            recipes.add(recipeIdMap.get(id));
        }

        return recipes;
    }

    @Override
    public Recipe get(int id) {


        return null;
    }

    @Override
    public Recipe insert(Recipe entityToInsert) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.RECIPE_NAME, entityToInsert.getName());
        cv.put(RecipeDbHelper.RECIPE_PHOTO, entityToInsert.getPhotoPath());
        cv.put(RecipeDbHelper.RECIPE_UPLOADED_BY, entityToInsert.getUploadedBy());
        cv.put(RecipeDbHelper.RECIPE_CATEGORY_FK, entityToInsert.getCategory().getCategoryId());

        long id = database.insert(mTableName, null, cv);
        entityToInsert.setRecipeId(id);

        database.close();

        return entityToInsert;
    }

    @Override
    public Recipe update(Recipe entityToUpdate) {
        SQLiteDatabase database = mDb.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RecipeDbHelper.RECIPE_NAME, entityToUpdate.getName());
        cv.put(RecipeDbHelper.RECIPE_PHOTO, entityToUpdate.getPhotoPath());
        cv.put(RecipeDbHelper.RECIPE_UPLOADED_BY, entityToUpdate.getUploadedBy());
        cv.put(RecipeDbHelper.RECIPE_CATEGORY_FK, entityToUpdate.getCategory().getCategoryId());

        database.update(mTableName, cv, RecipeDbHelper.RECIPE_ID + " = ? ",
                new String[]{String.valueOf(entityToUpdate.getRecipeId())});

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
