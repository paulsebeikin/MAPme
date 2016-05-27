package psyblaze.mapme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import Classes.Record;
import Classes.RecordHelper;
import Classes.Template;
import Classes.Web;

public class NewRecordActivity3 extends OrmLiteBaseActivity<RecordHelper> implements AppCompatCallback {

    //region UI Views
    Spinner environment_spin, number_spin, nat_cul_spin, growth_spin;
    CheckBox fruit, flower;
    EditText species;
    //endregion

    //region Objects
    Gson gson;
    SharedPreferences settings;
    Editor editor;
    Template template;
    ArrayAdapter env_adapter, num_adapter, natcul_adapter, growth_adapter;
    AppCompatDelegate delegate;
    //endregion

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record3);

        delegate = AppCompatDelegate.create(this,this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_new_record3);
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

        // get views
        fruit = (CheckBox) findViewById(R.id.fruit);
        flower = (CheckBox) findViewById(R.id.flower);
        species = (EditText) findViewById(R.id.species);

        // get values from arrays
        String[] env_values = getResources().getStringArray(R.array.Environments);
        String[] num_values = getResources().getStringArray(R.array.NumberObserved);
        String[] nat_cul_values = getResources().getStringArray(R.array.NaturalCultivated);
        String[] growth_values = getResources().getStringArray(R.array.GrowthForm);

        // setup environment spinner
        environment_spin = (Spinner) findViewById(R.id.environment_spinner);
        env_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, env_values);
        env_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        environment_spin.setAdapter(env_adapter);

        // setup number spinner
        number_spin = (Spinner)findViewById(R.id.numObserved_spinner);
        num_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, num_values);
        num_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        number_spin.setAdapter(num_adapter);

        // setup nat_cul spinner
        nat_cul_spin = (Spinner)findViewById(R.id.nat_cul_spinner);
        natcul_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nat_cul_values);
        natcul_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        nat_cul_spin.setAdapter(natcul_adapter);

        // setup growth spinner
        growth_spin = (Spinner)findViewById(R.id.growth_spinner);
        growth_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, growth_values);
        growth_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        growth_spin.setAdapter(growth_adapter);

        // Shared Preference restore
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        SharedPrefsRestore();
    }

    @Override
    public void onPause(){
        super.onPause();
        saveTemplate();
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPrefsRestore();
    }

    //endregion

    //region Activity Methods

    private void SharedPrefsRestore(){
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            environment_spin.setSelection(env_adapter.getPosition(template.environment));
            number_spin.setSelection(num_adapter.getPosition(template.numObserved));
            nat_cul_spin.setSelection(natcul_adapter.getPosition(template.natCul));
            growth_spin.setSelection(growth_adapter.getPosition(template.growth));
            species.setText(template.species);
            fruit.setChecked(template.fruit);
            flower.setChecked(template.flower);
        }
        else {
            template = new Template();
        }
    }

    private void saveTemplate(){
        // get editor ready
        editor = settings.edit();

        // update the template
        template.environment = environment_spin.getSelectedItem().toString();
        template.natCul = nat_cul_spin.getSelectedItem().toString();
        template.growth = growth_spin.getSelectedItem().toString();
        template.numObserved = number_spin.getSelectedItem().toString();
        template.species = species.getText().toString();
        template.fruit = fruit.isChecked();
        template.flower = flower.isChecked();

        String json = gson.toJson(template);
        editor.putString("template", json);
        editor.commit();
    }

    public void onSubmit(View view){
        saveTemplate();
        RecordHelper helper = new RecordHelper(this);
        helper.getWritableDatabase();
        RuntimeExceptionDao<Record,Integer> recordDao = getHelper().getRecordDao();

        Record toInsert = new Record(template);
        toInsert.setEmail(settings.getString("email",""));
        toInsert.setAdu(Integer.parseInt(settings.getString("adu","")));

        recordDao.create(toInsert);

        //List<Record> allRecords = recordDao.queryForAll();
        //for (Record r : allRecords) Log.i("record", r.toString());
        Web.postRecord(toInsert);

        //clear images from current template
        template.Reset();
        saveTemplate();

        // go back to home page
        Intent goHome = new Intent(this, HomeScreenActivity.class);
        startActivity(goHome);
        finish();
    }

    //endregion

    //region Toolbar Stuff
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
