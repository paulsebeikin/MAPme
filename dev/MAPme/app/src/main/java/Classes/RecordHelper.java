package Classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import psyblaze.mapme.R;

/**
 * Created by Paul on 5/24/2016.
 */
public class RecordHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME= "local.db";
    private static final int DATABASE_VERSION= 1;

    private Dao<Record,Integer> recordDao = null;
    private RuntimeExceptionDao<Record,Integer> recordRuntimeDao = null;

    public RecordHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(RecordHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Record.class);
        } catch (SQLException e) {
            Log.e(RecordHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(RecordHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Record.class, true);
            // after we drop the old tables, we create the new one
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(RecordHelper.class.getName(), "Can't drop table", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Record, Integer> getDao() throws SQLException {
        if (recordDao == null) {
            recordDao = getDao(Record.class);
        }
        return recordDao;
    }

    public RuntimeExceptionDao<Record, Integer> getRecordDao() {
        if (recordRuntimeDao == null) {
            recordRuntimeDao = getRuntimeExceptionDao(Record.class);
        }
        return recordRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        recordDao = null;
    }
}
