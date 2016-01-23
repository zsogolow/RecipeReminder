package shire.the.great.data.dao;

import android.content.Context;

import java.util.List;

/**
 * Interface outlining the minimal required methods when creating
 * a DAO object.
 *
 * @param <D> The Domain class we want to map this DAO to.
 * @param <T> The SQLiteOpenHelper subclass created to access
 *            the SQLite database framework.
 *
 * Created by ZachS on 1/21/2016.
 */
public interface IBaseDAO<D, T> {
    List<D> get();
    D get(int id);
    D insert(D entityToInsert);
    D update(D entityToUpdate);
    void delete(String whereClause, String[] whereArgs);
    void initializeDatabase(Context context);
}