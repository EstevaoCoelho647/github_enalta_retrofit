package com.project.estevao.apigit.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.estevao.apigit.model.entity.Repository;
import com.project.estevao.apigit.model.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Estevao on 08/08/2016.
 */
public class RepositoryRepository {

    public static void save(Repository repository) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = RepositoryContract.getContentValues(repository);

        db.insert(RepositoryContract.TABLE, null, values);

        db.close();
        databaseHelper.close();
    }

    public static void save(List<Repository> repositories) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.beginTransaction();
        for (Repository repository : repositories) {
            Log.e("saveRep", repository.getName());
            ContentValues values = RepositoryContract.getContentValues(repository);
            db.insert(RepositoryContract.TABLE, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public static List<Repository> findByIdWeb(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = RepositoryContract.IDWEB + " = ?";
        String[] params = {String.valueOf(id)};

        Cursor cursor = db.query(RepositoryContract.TABLE, RepositoryContract.columns, where, params, null, null, null);
        List<Repository> values = RepositoryContract.getRepositories(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }


    public static ArrayList<Repository> getByIdUser(Long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = RepositoryContract.IDUSER + " = ?";
        String[] params = {String.valueOf(id)};

        Cursor cursor = db.query(RepositoryContract.TABLE, RepositoryContract.columns, where, params, null, null, null);
        ArrayList<Repository> values = RepositoryContract.getRepositories(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }

    public static void deleteAll(){
        Log.d("Droping repositories", "");

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        db.delete(RepositoryContract.TABLE, null, null);

        db.close();
        databaseHelper.close();
    }

}
