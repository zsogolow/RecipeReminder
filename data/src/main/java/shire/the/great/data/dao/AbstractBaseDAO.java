package shire.the.great.data.dao;

import android.content.Context;

import java.util.List;

/**
 * The abstract base implementation of the IBaseDAO, and every
 * DAO object you create should inherit from this class.
 *
 * @param <D> The Domain class we want to map this DAO to.
 * @param <T> The SQLiteOpenHelper subclass created to access
 *            the SQLite database framework.
 *
 * Created by ZachS on 1/21/2016.
 */
public abstract class AbstractBaseDAO<D, T> implements IBaseDAO<D, T> {
    protected Context mContext;
    protected T mDb;
    protected String mTableName;

    public AbstractBaseDAO(Context context, String tblName) {
        mContext = context;
        mTableName = tblName;
        initializeDatabase(mContext);
    }

    /**
     * This method is called from the constructor, and super must
     * be called in the implementing classes constructor.
     *
     * The dbHelper passed here is the mDb instance above. It is okay
     * to initialize this instance being passed and not assign it.
     * @param context the same context used to construct this class to
     *                be passed to a subclass of the SQLiteOpenHelper.
     */
    @Override
    public abstract void initializeDatabase(Context context);

    @Override
    public abstract List<D> get();

    @Override
    public abstract D get(int id);

    @Override
    public abstract D insert(D entityToInsert);

    @Override
    public abstract D update(D entityToUpdate);

    @Override
    public abstract void delete(String whereClause, String[] whereArgs);
}