package shire.the.great.domain.models;

/**
 * Domain class RecipeTime.
 *
 * Created by ZachS on 1/23/2016.
 */
public class RecipeTime {
    private int mRecipeTimeId;
    private long mDuration;
    private int mRecipeId;

    public RecipeTime(int id, long duration, int recipeId) {
        mRecipeTimeId = id;
        mDuration = duration;
        mRecipeId = recipeId;
    }

    public int getRecipeTimeId() {
        return mRecipeTimeId;
    }

    public long getDuration() {
        return mDuration;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeTimeId(long recipeTimeId) {
        this.mRecipeTimeId = (int)recipeTimeId;
    }
}
