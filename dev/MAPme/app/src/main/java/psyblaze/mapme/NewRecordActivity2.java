package psyblaze.mapme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
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

public class NewRecordActivity2 extends OrmLiteBaseActivity<RecordHelper> implements AppCompatCallback{

    //region UI Views
    EditText country, province, town, desc;
    //endregion

    Boolean useGPS = true;

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
        toolbar.setPopupTheme(R.style.mapmeTheme);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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

        SharedPrefRestore();
        //initGeoTask();
    }

    protected void onPause(){
        super.onPause();
        SharedPrefCommit();
    }

    protected void onResume(){
        super.onResume();
        initGeoTask();
    }
    //endregion

    //region Activity Methods

    private void initGeoTask(){
        geocoder = new Geocoder(this, Locale.getDefault());
        Bundle bundle = getIntent().getExtras();
        double[] location = (double[]) bundle.get("location");
        new GeoCodeAsyncTask().execute(new Double[]{Double.valueOf(location[0]),
                Double.valueOf(location[1])});
    }

    private void SharedPrefCommit(){
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
        SharedPrefCommit();
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
        SharedPrefCommit();

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
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            /*desc.setText(template.desc);

            country.setText(template.country);
            province.setText(template.province);
            town.setText(template.town);*/
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

            // Shared Preference restore
            //SharedPrefRestore();
            settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
            gson = new Gson();
            displayDialog();
            boolean useGPS = settings.getBoolean("useGPS", true);

            if (result != null) {
                if (useGPS) {
                    //true branch
                    country.setText(result.getCountryName());
                    province.setText(result.getAdminArea());
                    town.setText(result.getLocality());
                }
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

    //region Helper methods
    private void displayDialog() {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("You have selected GPS coodinates.\n" +
                "Country and/or province have been automatically detected.\n" +
                "Would you like to use your Profile settings or the GPS data?");
        final Boolean res;
        dlgAlert.setTitle("MAPme New Record");
        dlgAlert.setPositiveButton("Profile Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
                editor = settings.edit();
                editor.putBoolean("useGPS", false);
                editor.commit();
                country.setText(settings.getString("country", ""));
                province.setText(settings.getString("province", ""));
                town.setText(settings.getString("town", ""));
            }
        });
        dlgAlert.setNegativeButton("GPS Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
                editor = settings.edit();
                editor.putBoolean("useGPS", true);
                editor.commit();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    //endregion
}
