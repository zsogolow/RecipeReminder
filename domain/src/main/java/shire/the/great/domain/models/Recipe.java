package shire.the.great.domain.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain class Recipe.
 *
 * Created by ZachS on 1/21/2016.
 */
public class Recipe {

    private int mRecipeId;
    private String mName;
    private String mPhotoPath;
    private String mUploadedBy;
    private List<RecipeTime> mAverageTimes;
    private List<Ingredient> mIngredients;
    private List<Instruction> mInstructions;
    private List<Note> mNotes;
    private RecipeCategory mCategory;

    public Recipe(int id,
                  String name,
                  String photoPath,
                  String uploadedBy,
                  List<Ingredient> ingredients,
                  List<Instruction> instructions,
                  List<Note> notes,
                  RecipeCategory category,
                  List<RecipeTime> averageTimes) {

        mRecipeId = id;
        mName = name;
        mPhotoPath = photoPath;
        mUploadedBy = uploadedBy;
        mIngredients = ingredients;
        mInstructions = instructions;
        mNotes = notes;
        mCategory = category;
        mAverageTimes = averageTimes;
    }

    public Recipe(int id,
                  String name,
                  String photoPath,
                  String uploadedBy,
                  RecipeCategory category) {

        this(id,
                name,
                photoPath,
                uploadedBy,
                new ArrayList<Ingredient>(),
                new ArrayList<Instruction>(),
                new ArrayList<Note>(),
                category,
                new ArrayList<RecipeTime>());
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public String getName() {
        return mName;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public String getUploadedBy() {
        return mUploadedBy;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Instruction> getInstructions() {
        return mInstructions;
    }

    public List<Note> getNotes() {
        return mNotes;
    }

    public List<RecipeTime> getAverageTime() {
        return mAverageTimes;
    }

    public RecipeCategory getCategory() {
        return mCategory;
    }

    public void setRecipeId(long recipeId) {
        this.mRecipeId = (int) recipeId;
    }

    public void addInstruction(Instruction instruction) {
        if (mInstructions == null) {
            mInstructions = new ArrayList<>();
        }

        mInstructions.add(instruction.getPosition(), instruction);
    }

    public void addIngredient(Ingredient ingredient) {
        if (mIngredients == null) {
            mIngredients = new ArrayList<>();
        }

        mIngredients.add(ingredient.getPosition(), ingredient);
    }

    public void addNote(Note note) {
        if (mNotes == null) {
            mNotes = new ArrayList<>();
        }

        mNotes.add(note);
    }

    public void addTime(RecipeTime recipeTime) {
        if (mAverageTimes == null) {
            mAverageTimes = new ArrayList<>();
        }

        mAverageTimes.add(recipeTime);
    }
}
