package shire.the.great.domain.models;

/**
 * Domain class Note.
 *
 * Created by ZachS on 1/21/2016.
 */
public class Note {
    private int mNoteId;
    private String mNote;
    private int mEntityId;

    public Note(int noteId,
                String note,
                int entityId) {
        mNoteId = noteId;
        mNote = note;
        mEntityId = entityId;
    }

    public int getNoteId() {
        return mNoteId;
    }

    public String getNote() {
        return mNote;
    }

    public int getEntityId() {
        return mEntityId;
    }

    public void setEntityId(long entityId) {
        this.mEntityId = (int) entityId;
    }
}
