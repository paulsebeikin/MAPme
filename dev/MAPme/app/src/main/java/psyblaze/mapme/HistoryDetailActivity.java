package psyblaze.mapme;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.text.DecimalFormat;

import Classes.Record;
import Classes.RecordHelper;

public class HistoryDetailActivity extends OrmLiteBaseActivity<RecordHelper> implements AppCompatCallback {

    //region Variables
    DecimalFormat df = new DecimalFormat("#.##");

    //region UI Views
    TextView date, proj, desc, img, gps_lat, gps_long, country, province, town;

    //region Objects
    RuntimeExceptionDao runtimeExceptionDao;
    AppCompatDelegate delegate;
    RecordHelper helper;
    Record record;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        // initialize Toolbar
        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_history_detail);
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

        // get UI Views
        date = (TextView) findViewById(R.id.date);
        proj = (TextView) findViewById(R.id.proj);
        desc = (TextView) findViewById(R.id.desc);
        img = (TextView) findViewById(R.id.imgs);
        gps_lat = (TextView) findViewById(R.id.gps_lat);
        gps_long = (TextView) findViewById(R.id.gps_long);
        country = (TextView) findViewById(R.id.country);
        province = (TextView) findViewById(R.id.province);
        town = (TextView) findViewById(R.id.town);

        // get record ID from History Activity
        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");
        helper = new RecordHelper(this);
        helper.getWritableDatabase();
        runtimeExceptionDao = getHelper().getRecordDao();
        record =  (Record) runtimeExceptionDao.queryForId(id);

        // set History Item data
        date.setText(record.getDate());
        proj.setText(record.getProject());
        desc.setText(record.getDesc());
        img.setText(record.getUrl());
        img.setText(getImageNames(record.getUrl()));
        gps_lat.setText(String.valueOf(df.format(record.getLatitude())));
        gps_long.setText(String.valueOf(df.format(record.getLongitude())));
        country.setText(record.getCountry());
        province.setText(record.getProvince());
        town.setText(record.getTown());
    }

    //region Formatter methods
    private String getImageNames(String paths) {
        String result;
        String arr [] = paths.split(";");
        String [] img1 = arr[0].split("/");
        String [] img2 = arr[1].split("/");
        String [] img3 = arr[2].split("/");

        int lastElement  = img1.length - 1;
        int lastElement2 = img2.length - 1;
        int lastElement3 = img3.length - 1;

        if (img2[lastElement2].equals("null")) result = img1[lastElement];
        else if (img3[lastElement3].equals("null")) result = img1[lastElement] + "\n" + img2[lastElement2];
        else result = img1[lastElement] + "\n" + img2[lastElement2] + "\n" + img3[lastElement3];
        return result;
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
