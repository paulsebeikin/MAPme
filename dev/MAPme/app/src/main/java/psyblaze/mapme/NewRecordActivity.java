package psyblaze.mapme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRecordActivity extends AppCompatActivity {

    Spinner proj_spinner;
    TextView datePicker;
    SelectDateFragment datePickerFragment;
    EditText gps_lat, gps_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);

        String[] values = getResources().getStringArray(R.array.Projects);

        // get datePicker element and set default date as today
        datePicker = (TextView) findViewById(R.id.date_picker);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        datePicker.setText(sdf.format(new Date()));

        // fetch spinner object from the layout
        proj_spinner = (Spinner)findViewById(R.id.project_spinner);

        // create adapter for spinner
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);

        // configure drop-down layout style
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        proj_spinner.setAdapter(spinnerAdapter);

        gps_lat = (EditText) findViewById(R.id.gps_lat);
        gps_long = (EditText) findViewById(R.id.gps_long);

        // Grahamstown test
        gps_long.setText(Double.toString(-33.312408));
        gps_lat.setText(Double.toString(26.519080));

        // Example Tests for Reverse GeoCoding

        //Joburg test
        //gps_long.setText(Double.toString(-26.204110));
        //gps_lat.setText(Double.toString(28.030736));

        //Seattle test
        //gps_long.setText(Double.toString(47.603585));
        //gps_lat.setText(Double.toString(-122.344180));

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
            }
        });
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void navigateNext(View view){
        Double lng = Double.parseDouble(gps_long.getText().toString());
        Double lat = Double.parseDouble(gps_lat.getText().toString());
        Intent nextInt = new Intent(this, NewRecordActivity2.class);
        nextInt.putExtra("location", new Double[] {lng, lat});
        startActivity(nextInt);
    }

}
