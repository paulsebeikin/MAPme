package psyblaze.mapme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class NewRecordActivity3 extends AppCompatActivity {

    Spinner environment_spin;
    Spinner number_spin;
    Spinner nat_cul_spin;
    Spinner growth_spin;

    ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record3);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);

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
