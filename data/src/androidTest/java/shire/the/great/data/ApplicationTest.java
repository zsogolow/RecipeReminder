package shire.the.great.data;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.List;

import shire.the.great.data.dao.IngredientDAO;
import shire.the.great.data.dao.InstructionDAO;
import shire.the.great.data.dao.NoteDAO;
import shire.the.great.data.dao.RecipeCategoryDAO;
import shire.the.great.data.dao.RecipeDAO;
import shire.the.great.data.dao.RecipeTimeDAO;
import shire.the.great.data.db.RecipeDbHelper;
import shire.the.great.domain.models.Ingredient;
import shire.the.great.domain.models.Instruction;
import shire.the.great.domain.models.Note;
import shire.the.great.domain.models.Recipe;
import shire.the.great.domain.models.RecipeCategory;
import shire.the.great.domain.models.RecipeTime;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testGetRecipe() throws Exception {

        RecipeDAO dao = new RecipeDAO(getContext(), RecipeDbHelper.RECIPE_TABLE);

        dao.get();
    }

    public void testInserts() throws Exception {
        RecipeDAO recipeDAO = new RecipeDAO(getContext(), RecipeDbHelper.RECIPE_TABLE);
        IngredientDAO ingredientDAO = new IngredientDAO(getContext(), RecipeDbHelper.INGREDIENT_TABLE);
        InstructionDAO instructionDAO = new InstructionDAO(getContext(), RecipeDbHelper.INSTRUCTION_TABLE);
        RecipeCategoryDAO recipeCategoryDAO = new RecipeCategoryDAO(getContext(), RecipeDbHelper.RECIPE_CATEGORY_TABLE);
        RecipeTimeDAO recipeTimeDAO = new RecipeTimeDAO(getContext(), RecipeDbHelper.RECIPE_TIME_TABLE);
        NoteDAO noteDAO = new NoteDAO(getContext(), RecipeDbHelper.NOTE_TABLE);


        List<Ingredient> ingredientList = new ArrayList<>();
        List<Instruction> instructionList = new ArrayList<>();
        List<Note> noteList = new ArrayList<>();
        List<RecipeTime> recipeTimesList = new ArrayList<>();


        RecipeCategory recipeCategory = new RecipeCategory(-1, "Breakfast");
        RecipeCategory recipeCategory1 = new RecipeCategory(-1, "Lunch");
        RecipeCategory recipeCategory2 = new RecipeCategory(-1, "Dinner");


        Recipe recipe = new Recipe(-1,
                "Goolosh",
                "/path/to/photo",
                "Zach",
                ingredientList,
                instructionList,
                noteList,
                recipeCategory,
                recipeTimesList);

        recipeCategoryDAO.insert(recipeCategory);
        recipeCategoryDAO.insert(recipeCategory1);
        recipeCategoryDAO.insert(recipeCategory2);

        recipeDAO.insert(recipe);

        RecipeTime recipeTime = new RecipeTime(-1, 30, 1);

        Note note = new Note(-1, "hi", 1);

        noteDAO.insert(note);
        recipeTimeDAO.insert(recipeTime);
    }
}