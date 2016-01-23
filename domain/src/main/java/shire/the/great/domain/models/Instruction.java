package shire.the.great.domain.models;

/**
 * Domain class Instruction.
 *
 * Created by ZachS on 1/21/2016.
 */
public class Instruction {
    private int mInstructionId;
    private String mInstruction;
    private int mPosition;
    private int mRecipeId;

    public Instruction(int instructionId,
                       String instruction,
                       int position,
                       int recipeId) {

        mInstructionId = instructionId;
        mInstruction = instruction;
        mPosition = position;
        mRecipeId = recipeId;
    }

    public int getInstructionId() {
        return mInstructionId;
    }

    public String getInstruction() {
        return mInstruction;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setInstructionId(long instructionId) {
        this.mInstructionId = (int) instructionId;
    }
}
