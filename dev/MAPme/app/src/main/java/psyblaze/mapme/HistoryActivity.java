package psyblaze.mapme;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import Classes.ListViewAdapter;
import Classes.Record;
import Classes.RecordHelper;

public class HistoryActivity extends OrmLiteBaseActivity<RecordHelper> implements AppCompatCallback {

    //region Variables
    private static final int DISPLAY_RECORD = 1;
    List<Record> allRecords;

    //region UI Views
    ListView historyView;
    SwipeLayout swipeLayout;
    //endregion

    //region Objects
    //HistoryArrayAdapter listViewAdapter;
    ListViewAdapter listViewAdapter;
    RuntimeExceptionDao runtimeExceptionDao;
    AppCompatDelegate delegate;
    RecordHelper helper;
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // initialize Toolbar
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_history);
        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        delegate.setSupportActionBar(action_bar);
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        action_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        allRecords = selectRecords();
        historyView = (ListView) findViewById(R.id.historyView);
        listViewAdapter = new ListViewAdapter(this, allRecords);
        historyView.setAdapter(listViewAdapter);
        listViewAdapter.setMode(Attributes.Mode.Single);

    }

    private List<Record> selectRecords(){
        List<Record> selected = null;
        helper = new RecordHelper(this);
        helper.getWritableDatabase();
        runtimeExceptionDao = getHelper().getRecordDao();
        try {
            QueryBuilder<Record, Integer> queryBuilder = runtimeExceptionDao.queryBuilder();
            Where<Record, Integer> where = queryBuilder.where();
            where.eq("deleted", false);
            PreparedQuery preparedQuery = queryBuilder.prepare();
            selected = runtimeExceptionDao.query(preparedQuery);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return selected;
    }

    protected void onPause(){
        super.onPause();
        /*try {
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }*/
    }
    //endregion

    //region Toolbar Stuff
    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
    //endregion
}
