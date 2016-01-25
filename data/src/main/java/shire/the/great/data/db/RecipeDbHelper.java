package shire.the.great.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Subclass of the SQLiteOpenHelper which is the access
 * point in the SQLite framework.
 *
 * Created by ZachS on 1/21/2016.
 */
public class RecipeDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recipes.db";

    ////////////// TABLE DEFINITIONS //////////////
    public static final String RECIPE_TABLE = "Recipe_tbl";
    public static final String RECIPE_ID = "RecipeId_col";
    public static final String RECIPE_NAME = "RecipeName_col";
    public static final String RECIPE_PHOTO = "RecipePhoto_col";
    public static final String RECIPE_UPLOADED_BY = "UploadedBy_col";
    public static final String RECIPE_CATEGORY_FK = "RecipeCategoryFK_col";

    public static final String INGREDIENT_TABLE = "Ingredient_tbl";
    public static final String INGREDIENT_ID = "IngredientId_col";
    public static final String INGREDIENT_NAME = "IngredientName_col";
    public static final String INGREDIENT_AMOUNT = "IngredAmount_col";
    public static final String INGREDIENT_POSITION = "IngredPosition_col";
    public static final String INGREDIENT_RECIPE_FK = "RecipeFK_col";

    public static final String INSTRUCTION_TABLE = "Instruction_tbl";
    public static final String INSTRUCTION_ID = "InstructionId_col";
    public static final String INSTRUCTION_TEXT = "InstructionText_col";
    public static final String INSTRUCTION_POSITION = "InstructPosition_col";
    public static final String INSTRUCTION_RECIPE_FK = "RecipeFK_col";

    public static final String NOTE_TABLE = "Note_tbl";
    public static final String NOTE_ID = "NoteId_col";
    public static final String NOTE_TEXT = "NoteText_col";
    public static final String NOTE_ENTITY_FK = "NoteEntityFK_col";

    public static final String RECIPE_CATEGORY_TABLE = "RecipeCategory_tbl";
    public static final String RECIPE_CATEGORY_ID = "RecipeCategoryId_col";
    public static final String RECIPE_CATEGORY = "RecipeCategory_col";

    public static final String RECIPE_TIME_TABLE = "RecipeTime_tbl";
    public static final String RECIPE_TIME_ID = "RecipeTimId_col";
    public static final String RECIPE_COOK_DURATION = "CookDuration_col";
    public static final String RECIPE_TIME_ID_FK = "RecipeIdFK_col";

    ////////////// CREATE STATEMENTS //////////////
    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE " + RECIPE_TABLE
            + "("
            + RECIPE_ID + " INTEGER PRIMARY KEY, "
            + RECIPE_NAME + " TEXT, "
            + RECIPE_PHOTO + " TEXT, "
            + RECIPE_UPLOADED_BY + " TEXT, "
            + RECIPE_CATEGORY_FK + " INTEGER, "
            + "FOREIGN KEY (" + RECIPE_CATEGORY_FK + ") "
            + "REFERENCES " + RECIPE_CATEGORY_TABLE + "(" + RECIPE_CATEGORY_ID + ")"
            + ");";

    private static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + INGREDIENT_TABLE
            + "("
            + INGREDIENT_ID + " INTEGER PRIMARY KEY, "
            + INGREDIENT_NAME + " TEXT, "
            + INGREDIENT_AMOUNT + " TEXT, "
            + INGREDIENT_POSITION + " INTEGER, "
            + INGREDIENT_RECIPE_FK + " INTEGER, "
            + "FOREIGN KEY (" + INGREDIENT_RECIPE_FK + ") "
            + "REFERENCES " + RECIPE_TABLE + "(" + RECIPE_ID + ")"
            + ");";

    private static final String CREATE_INSTRUCTION_TABLE = "CREATE TABLE " + INSTRUCTION_TABLE
            + "("
            + INSTRUCTION_ID + " INTEGER PRIMARY KEY, "
            + INSTRUCTION_TEXT + " TEXT, "
            + INSTRUCTION_POSITION + " INTEGER, "
            + INSTRUCTION_RECIPE_FK + " INTEGER, "
            + "FOREIGN KEY (" + INSTRUCTION_RECIPE_FK + ") "
            + "REFERENCES " + RECIPE_TABLE + "(" + RECIPE_ID + ")"
            + ");";

    private static final String CREATE_NOTE_TABLE = "CREATE TABLE " + NOTE_TABLE
            + "("
            + NOTE_ID + " INTEGER PRIMARY KEY, "
            + NOTE_TEXT + " TEXT, "
            + NOTE_ENTITY_FK + " INTEGER"
            + ");";

    private static final String CREATE_RECIPE_CATEGORY_TABLE = "CREATE TABLE " + RECIPE_CATEGORY_TABLE
            + "("
            + RECIPE_CATEGORY_ID + " INTEGER PRIMARY KEY, "
            + RECIPE_CATEGORY + " TEXT"
            + ");";

    private static final String CREATE_RECIPE_TIME_TABLE = "CREATE TABLE " + RECIPE_TIME_TABLE
            + "("
            + RECIPE_TIME_ID + " INTEGER PRIMARY KEY, "
            + RECIPE_COOK_DURATION + " INTEGER, "
            + RECIPE_TIME_ID_FK + " INTEGER, "
            + "FOREIGN KEY (" + RECIPE_TIME_ID_FK + ") "
            + "REFERENCES " + RECIPE_TABLE + "(" + RECIPE_ID + ")"
            + ");";

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECIPE_CATEGORY_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENT_TABLE);
        db.execSQL(CREATE_INSTRUCTION_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_RECIPE_TIME_TABLE);

        ContentValues cv = new ContentValues();
        cv.put(RECIPE_CATEGORY, "General");
        db.insert(RECIPE_CATEGORY_TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
