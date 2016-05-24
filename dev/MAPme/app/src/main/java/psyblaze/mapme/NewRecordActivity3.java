package psyblaze.mapme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import Classes.Template;

public class NewRecordActivity3 extends AppCompatActivity {

    // UI Views
    Spinner environment_spin;
    Spinner number_spin;
    Spinner nat_cul_spin;
    Spinner growth_spin;
    CheckBox fruit, flower;
    EditText species;

    //Objects
    Gson gson;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Template template;
    ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record3);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);

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
        environment_spin = (Spinner)findViewById(R.id.environment_spinner);
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, env_values);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        environment_spin.setAdapter(spinnerAdapter);

        // setup number spinner
        number_spin = (Spinner)findViewById(R.id.numObserved_spinner);
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, num_values);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        number_spin.setAdapter(spinnerAdapter);

        // setup nat_cul spinner
        nat_cul_spin = (Spinner)findViewById(R.id.nat_cul_spinner);
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nat_cul_values);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        nat_cul_spin.setAdapter(spinnerAdapter);

        // setup growth spinner
        growth_spin = (Spinner)findViewById(R.id.growth_spinner);
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, growth_values);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        growth_spin.setAdapter(spinnerAdapter);

        // Shared Preference restore
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            species.setText(template.species);
            fruit.setChecked(template.fruit);
            flower.setChecked(template.flower);
        }
        else {
            template = new Template();

        }

    }

    public void onDestroy(){
        super.onDestroy();
        saveTemplate();
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
}
