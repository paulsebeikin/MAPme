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


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Classes.Template;
import Classes.Web;
import Fragments.SelectDateFragment;

public class NewRecordActivity extends AppCompatActivity {

    // Class Level Variables
    private static final int GET_COORDS = 1;

    // UI Views
    Spinner proj_spinner, source_spinner;
    TextView datePicker;
    EditText gps_lat, gps_long, gps_alt;

    //Objects
    SelectDateFragment datePickerFragment;
    Gson gson;
    SharedPreferences settings;
    Editor editor;
    Template template;
    ArrayAdapter projAdapter, sourceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

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

        // get UI elements
        datePicker = (TextView) findViewById(R.id.date_picker);
        proj_spinner = (Spinner)findViewById(R.id.project_spinner);
        source_spinner = (Spinner)findViewById(R.id.source_spinner);
        gps_lat = (EditText) findViewById(R.id.gps_lat);
        gps_long = (EditText) findViewById(R.id.gps_long);
        gps_alt = (EditText) findViewById(R.id.gps_alt);

        // create adapter for spinner
        projAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Web.projects);
        // attaching data adapter to spinner
        proj_spinner.setAdapter(projAdapter);


        String [] values = getResources().getStringArray(R.array.source);
        sourceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
        //sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        source_spinner.setAdapter(sourceAdapter);

        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        SharedPrefRestore();

        // Permission to turn on location service

        /*LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }*/
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPrefRestore();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTemplate();
    }

    private void SharedPrefRestore(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String json = settings.getString("template", null);
        if (json != null) {
            template = gson.fromJson(json, Template.class);
            if (template.location[0] != 0.0) gps_lat.setText(template.location[0].toString());
            if (template.location[1] != 0.0) gps_long.setText(template.location[1].toString());
            if (template.altitude != 0.0) gps_alt.setText(template.altitude.toString());
            datePicker.setText(sdf.format(template.dt));
            proj_spinner.setSelection(projAdapter.getPosition(template.project));
            source_spinner.setSelection(sourceAdapter.getPosition(template.source));
        }
        else {
            template = new Template();
            datePicker.setText(sdf.format(new Date()));
        }
    }

    public void getMap(View view){
        // set up an initial marker for the map, to pass to the MapActivity
        LatLng startMarker;
        if (!gps_long.getText().toString().isEmpty() && !gps_lat.getText().toString().isEmpty()) {
            double lng = Double.parseDouble(gps_long.getText().toString());
            double lat = Double.parseDouble(gps_lat.getText().toString());
            startMarker = new LatLng(lat, lng);
        }
        // no GPS coordinates have previously been filled in so just put the startMarker at default pos.
        else startMarker = new LatLng(0.0,0.0);

        // start MapActivity and pass the startMarker
        Intent mapInt = new Intent(this, MapActivity.class);
        mapInt.putExtra("startMarker",startMarker);
        mapInt.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(mapInt, GET_COORDS);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

        }
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
        if (!validate()){
            Toast.makeText(this, "Required fields are empty.", Toast.LENGTH_LONG).show();
        }
        else {
            Double lng = Double.parseDouble(gps_long.getText().toString());
            Double lat = Double.parseDouble(gps_lat.getText().toString());
            Intent nextInt = new Intent(this, NewRecordActivity2.class);
            Bundle b = new Bundle();
            b.putDoubleArray("location", new double[]{lat,lng});
            nextInt.putExtras(b);
            //nextInt.putExtra("location", new Double[]{lat,lng});
            startActivity(nextInt);
        }
    }

    private boolean validate(){
        if (gps_long.getText().toString().isEmpty()) return false;
        if (gps_lat.getText().toString().isEmpty()) return false;
        if (gps_alt.getText().toString().isEmpty()) return false;
        if (datePicker.getText().toString().isEmpty()) return false;
        if (proj_spinner.getSelectedItem().toString().isEmpty()) return false;
        return true;
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
        if (!long_tmp.isEmpty() && !lat_tmp.isEmpty()) {
            template.location = new Double[]{Double.parseDouble(lat_tmp), Double.parseDouble(long_tmp)};
        }
        if (!alt_tmp.isEmpty()) {
            template.altitude = Double.parseDouble(alt_tmp);
        }
        template.project = proj_spinner.getSelectedItem().toString();
        template.source = source_spinner.getSelectedItem().toString();

        String json = gson.toJson(template);

        editor.putString("template", json);
        editor.commit();
    }
}
