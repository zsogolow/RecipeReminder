package shire.the.great.domain.models;

/**
 * Domain class Ingredient.
 *
 * Created by ZachS on 1/21/2016.
 */
public class Ingredient {
    private int mIngredientId;
    private String mName;
    private String mAmount;
    private int mPosition;
    private int mRecipeId;

    public Ingredient(int ingredientId,
                      String name,
                      String amount,
                      int position,
                      int recipeId) {

        mIngredientId = ingredientId;
        mName = name;
        mAmount = amount;
        mPosition = position;
        mRecipeId = recipeId;
    }

    public int getIngredientId() {
        return mIngredientId;
    }

    public String getName() {
        return mName;
    }

    public String getAmount() {
        return mAmount;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setIngredientId(long id) {
        this.mIngredientId = (int) id;
    }

}



