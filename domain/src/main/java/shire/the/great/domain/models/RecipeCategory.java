package shire.the.great.domain.models;

import java.io.Serializable;

/**
 * Domain class RecipeCategory.
 *
 * Created by ZachS on 1/21/2016.
 */
public class RecipeCategory implements Serializable {
    private int mCategoryId;
    private String mName;

    public RecipeCategory(int categoryId, String name) {
        mCategoryId = categoryId;
        mName = name;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public String getName() {
        return mName;
    }

    public void setRecipeCategoryId(long recipeCategoryId) {
        this.mCategoryId = (int)recipeCategoryId;
    }

    @Override
    public int hashCode() {
        return mName.hashCode() +  mCategoryId * 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RecipeCategory) {
            RecipeCategory recipeCategory = (RecipeCategory) obj;
            if (mName.equals(recipeCategory.mName) && mCategoryId == recipeCategory.mCategoryId) {
                return true;
            }
        }
        return false;
    }
}
