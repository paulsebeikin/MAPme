package psyblaze.mapme;

import android.content.Context;
import android.content.SharedPreferences;
import  android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import Classes.Template;
import Classes.Web;

public class ProfileActivity extends AppCompatActivity {

    //region UI components
    Spinner country_spin;
    Spinner province_spin;
    Spinner project_spin;

    ArrayAdapter projAdapter;
    ArrayAdapter countryAdapter;
    ArrayAdapter provinceAdapter;

    TextView name, email, adu, town, otherCountry;
    //endregion

    //region Objects
    ArrayAdapter spinnerAdapter;
    Editor editor;
    SharedPreferences settings;
    Template template;
    Gson gson;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        action_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name = (TextView) findViewById(R.id.nameTemp);
        email = (TextView) findViewById(R.id.emailTemp);
        adu = (TextView) findViewById(R.id.aduTemp);
        town = (TextView) findViewById(R.id.profNearestTown);
        otherCountry = (TextView) findViewById(R.id.countryOther);


        String countries [] = getResources().getStringArray(R.array.countryArr);
        String provinces [] = getResources().getStringArray(R.array.provincesArr);

        // set project adapter
        project_spin = (Spinner) findViewById(R.id.profProject);
        projAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Web.projects);
        projAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        project_spin.setAdapter(projAdapter);

        // set country adapter
        country_spin = (Spinner) findViewById(R.id.profCountry);
        countryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        country_spin.setAdapter(countryAdapter);

        // set province adapter -- defaulted to off until South Africa
        province_spin = (Spinner) findViewById(R.id.profProvince);
        provinceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        province_spin.setAdapter(provinceAdapter);
        province_spin.setEnabled(false);

        country_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                checkCountry(item.toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // get the shared preferences
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        SharedPrefRestore();
    }

    private void SharedPrefRestore() {
        String json = settings.getString("template", null);
        if (json != null) {
            template = gson.fromJson(json, Template.class);
            name.setText(settings.getString("username", ""));
            email.setText(settings.getString("email", ""));
            adu.setText(settings.getString("adu", ""));
            town.setText(settings.getString("town",""));
        } else {
            template = new Template();
        }
    }

    private void checkCountry(String country) {
        if (country.equals("South Africa")) {
            province_spin.setEnabled(true);
            otherCountry.setEnabled(false);
        }
        else if (country.equals("Other, please specify below")) {
            province_spin.setEnabled(false);
            otherCountry.setEnabled(true);
        }
        else {
            province_spin.setEnabled(false);
            otherCountry.setEnabled(false);
        }
    }


    private void saveTemplate () {
        editor = settings.edit();

        if (country_spin.getSelectedItem().toString().equals("")) {
            Toast.makeText(this, "Invalid country selected", Toast.LENGTH_LONG).show();
        }
        else if (country_spin.getSelectedItem().toString().equals("Other, please specify below")){
            template.country = otherCountry.getText().toString();
            template.province = province_spin.getSelectedItem().toString();
            template.town = town.getText().toString();
            template.project = project_spin.getSelectedItem().toString();

            String json = gson.toJson(template);
            editor.putString("template", json);
            editor.commit();
        }
        else {
            template.country = country_spin.getSelectedItem().toString();
            template.province = province_spin.getSelectedItem().toString();
            template.town = town.getText().toString();
            template.project = project_spin.getSelectedItem().toString();

            String json = gson.toJson(template);
            editor.putString("template", json);
            editor.commit();
        }
    }

    public void saveProfileSettings (View view) {
        saveTemplate();
        Toast.makeText(this, "Profile settings saved", Toast.LENGTH_LONG).show();
        onBackPressed();
    }

    private void SharedPrefsRestore(){
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            country_spin.setSelection(countryAdapter.getPosition(template.country));
            province_spin.setSelection(provinceAdapter.getPosition(template.province));
            project_spin.setSelection(projAdapter.getPosition(template.country));
        }
        else {
            template = new Template();
        }
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
}
