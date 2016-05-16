package psyblaze.mapme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRecordActivity extends AppCompatActivity {

    Spinner proj_spinner;
    TextView datePicker;
    SelectDateFragment datePickerFragment;

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
        Intent nextInt = new Intent(this, NewRecordActivity2.class);
        startActivity(nextInt);
    }

}
