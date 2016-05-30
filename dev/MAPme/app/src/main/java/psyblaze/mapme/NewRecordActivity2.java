package psyblaze.mapme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import Classes.Record;
import Classes.RecordHelper;
import Classes.Template;
import Classes.Web;

public class NewRecordActivity2 extends OrmLiteBaseActivity<RecordHelper> implements AppCompatCallback{

    //region UI Views
    EditText country, province, town, desc;
    //endregion

    //region Objects
    Gson gson;
    SharedPreferences settings;
    Editor editor;
    Template template;
    Geocoder geocoder;
    AppCompatDelegate delegate;
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record2);

        // initialize toolbar
        delegate = AppCompatDelegate.create(this,this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_new_record2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mapme_toolbar);
        delegate.setSupportActionBar(toolbar);
        delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        country = (EditText) findViewById(R.id.country);
        province = (EditText) findViewById(R.id.province);
        town = (EditText) findViewById(R.id.town);
        desc = (EditText) findViewById(R.id.desc_text);

        // Shared Preference restore
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        SharedPrefRestore();

        geocoder = new Geocoder(this, Locale.getDefault());
        Bundle bundle = getIntent().getExtras();
        double[] location = (double[]) bundle.get("location");
        new GeoCodeAsyncTask().execute(new Double[]{Double.valueOf(location[0]),
                    Double.valueOf(location[1])});
    }

    protected void onPause(){
        super.onPause();
        saveTemplate();
    }

    protected void onResume(){
        super.onResume();
        SharedPrefRestore();
    }
    //endregion

    //region Activity Methods

    private void saveTemplate(){
        // get editor ready
        editor = settings.edit();

        // update the template
        template.country = country.getText().toString();
        template.province = province.getText().toString();
        template.town = town.getText().toString();
        template.desc = desc.getText().toString();

        String json = gson.toJson(template);
        editor.putString("template", json);
        editor.commit();
    }

    public void onSubmit(View view){
        saveTemplate();
        RecordHelper helper = new RecordHelper(this);
        helper.getWritableDatabase();
        RuntimeExceptionDao<Record,Integer> recordDao = getHelper().getRecordDao();

        final Record toInsert = new Record(template);
        toInsert.setEmail(settings.getString("email",""));
        String aduStr = settings.getString("adu","");
        toInsert.setAdu(Integer.parseInt(aduStr));

        recordDao.create(toInsert);

        //clear images from current template
        template.Reset();
        saveTemplate();

        //submitRecordAsyncTask submit = new submitRecordAsyncTask(toInsert);
        //submit.execute();

        // go back to home page
        Intent goHome = new Intent(this, HomeScreenActivity.class);
        startActivity(goHome);
        finish();


    }

    public void navigateNext(View view){
        Intent nextInt = new Intent(this, NewRecordActivity3.class);
        startActivity(nextInt);
    }

    private void SharedPrefRestore(){
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            desc.setText(template.desc);
            country.setText(template.country);
            province.setText(template.province);
            town.setText(template.town);
        }
        else {
            template = new Template();
        }
    }

    //endregion

    //region Asynchronous Methods

    private class GeoCodeAsyncTask extends AsyncTask<Double, Void, Address> {
        @Override
        protected Address doInBackground(Double... location) {
            try {
                List<Address> addresses = geocoder.getFromLocation(location[0], location[1], 1);
                return addresses.get(0);
            }
            catch (IOException ex){
                return null;
            }
            catch (IndexOutOfBoundsException ex) {
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Address result) {
            if (result != null) {
                country.setText(result.getCountryName());
                province.setText(result.getAdminArea());
                town.setText(result.getLocality());
            }
        }
    }

    /*public class submitRecordAsyncTask extends AsyncTask<Record, Void, Boolean> {

        Record toInsert;
        submitRecordAsyncTask (Record record) {
            toInsert = record;
        }
        @Override
        protected Boolean doInBackground(Record... record) {
            return Web.postRecord(toInsert);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_home, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeInt = new Intent(this, HomeScreenActivity.class);
                startActivity(homeInt);
                return true;
            case R.id.action_logout:
                Toast.makeText(this,"Logging out...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                Intent aboutInt = new Intent(this, AboutActivity.class);
                startActivity(aboutInt);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion
}
