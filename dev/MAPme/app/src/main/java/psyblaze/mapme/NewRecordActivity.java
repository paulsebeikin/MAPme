package psyblaze.mapme;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Classes.Template;
import Fragments.SelectDateFragment;

public class NewRecordActivity extends AppCompatActivity {

    // UI Views
    Spinner proj_spinner;
    TextView datePicker;
    EditText gps_lat, gps_long, gps_alt;

    //Objects
    SelectDateFragment datePickerFragment;
    Gson gson;
    SharedPreferences settings;
    Editor editor;
    Template template;
    //MapFragment gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        // get UI elements
        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        datePicker = (TextView) findViewById(R.id.date_picker);
        proj_spinner = (Spinner)findViewById(R.id.project_spinner);
        gps_lat = (EditText) findViewById(R.id.gps_lat);
        gps_long = (EditText) findViewById(R.id.gps_long);
        gps_alt = (EditText) findViewById(R.id.gps_alt);

        String[] values = getResources().getStringArray(R.array.Projects);
        // create adapter for spinner
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
        // configure drop-down layout style
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // attaching data adapter to spinner
        proj_spinner.setAdapter(spinnerAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Shared Preference restore
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            gps_long.setText(template.location[0].toString());
            gps_lat.setText(template.location[1].toString());
            gps_alt.setText(template.altitude.toString());
            datePicker.setText(sdf.format(template.dt));
            for (int i = 0; i < spinnerAdapter.getCount(); i++){
                if (proj_spinner.getItemAtPosition(i) == template.project) proj_spinner.setSelection(i);
            }
        }
        else {
            template = new Template();
            datePicker.setText(sdf.format(new Date()));
        }

        setSupportActionBar(action_bar);

        // Permission to turn on location service

        /*LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }*/
    }

    public void getMap(View view){
        Intent mapInt = new Intent(this, MapActivity.class);
        startActivity(mapInt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTemplate();
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

    public void showDatePickerDialog(View view){
        datePickerFragment = new SelectDateFragment();
        Bundle args = new Bundle();
        datePickerFragment.setArguments(args);

        datePickerFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker view, int year, int month, int day){
                datePicker.setText(day + "/" + month + "/" + year);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date;
                try {
                    date = sdf.parse(day + "/" + month + "/" + year);
                }
                catch (ParseException ex){
                   date = null;
                }
                template.dt = date;
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void navigateNext(View view){
        Double lng = Double.parseDouble(gps_long.getText().toString());
        Double lat = Double.parseDouble(gps_lat.getText().toString());
        Intent nextInt = new Intent(this, NewRecordActivity2.class);
        nextInt.putExtra("location", new Double[]{lng, lat});
        startActivity(nextInt);
    }

    public void addImage(View view) {
        Intent nextInt = new Intent(this, AddImage.class);
        startActivity(nextInt);
    }

    private void saveTemplate(){
        // get editor ready
        editor = settings.edit();

        // update the template
        String long_tmp = gps_long.getText().toString();
        String lat_tmp = gps_lat.getText().toString();
        String alt_tmp = gps_alt.getText().toString();
        template.location = new Double[]{Double.parseDouble(long_tmp), Double.parseDouble(lat_tmp)};
        template.altitude = Double.parseDouble(alt_tmp);
        template.project = proj_spinner.getSelectedItem().toString();

        String json = gson.toJson(template);
        editor.putString("template", json);
        editor.commit();
    }
}
