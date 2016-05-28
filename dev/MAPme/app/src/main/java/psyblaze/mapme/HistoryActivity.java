package psyblaze.mapme;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.swipe.SwipeLayout;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.Help;
import Classes.HelpArrayAdapter;
import Classes.HistoryArrayAdapter;
import Classes.Record;
import Classes.RecordHelper;

public class HistoryActivity extends OrmLiteBaseActivity<RecordHelper> implements AppCompatCallback {

    //region Variables
    private static final int DISPLAY_RECORD = 1;

    //region UI Views
    ListView historyView;
    //endregion

    //region Objects
    HistoryArrayAdapter historyArrayAdapter;
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

        helper = new RecordHelper(this);
        helper.getWritableDatabase();
        runtimeExceptionDao = getHelper().getRecordDao();

        List<Record> allRecords = runtimeExceptionDao.queryForAll();

        historyView = (ListView) findViewById(R.id.historyView);
        historyArrayAdapter = new HistoryArrayAdapter(this, allRecords);
        historyView.setAdapter(historyArrayAdapter);

        historyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(getApplicationContext(), HistoryDetailActivity.class);
                detailsIntent.putExtra("id", historyArrayAdapter.getRecord(position).getId());
                startActivity(detailsIntent);
            }
        });
    }
    //endregion

    //region Activity Methods
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
