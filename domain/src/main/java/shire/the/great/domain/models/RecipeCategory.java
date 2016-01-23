package shire.the.great.domain.models;

/**
 * Domain class RecipeCategory.
 *
 * Created by ZachS on 1/21/2016.
 */
public class RecipeCategory {
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
}
