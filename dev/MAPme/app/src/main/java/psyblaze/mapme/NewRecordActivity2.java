package psyblaze.mapme;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewRecordActivity2 extends AppCompatActivity {

    private String api_key = "AIzaSyAAOj2-rMW7agCLakPjq0pPxxMPlilq7hw";
    String url_str = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=" + api_key;
    Geocoder geocoder;

    EditText country, province, town;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record2);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);

        country = (EditText) findViewById(R.id.country);
        province = (EditText) findViewById(R.id.province);
        town = (EditText) findViewById(R.id.town);

        geocoder = new Geocoder(this, Locale.getDefault());
        Bundle bundle = getIntent().getExtras();
        Double [] location = (Double[]) bundle.get("location");
        new GeoCodeAsyncTask().execute(location);
    }

    private class GeoCodeAsyncTask extends AsyncTask<Double, Void, Address> {
        @Override
        protected Address doInBackground(Double... location) {
            try {
                List<Address> addresses = geocoder.getFromLocation(location[0], location[1], 1);
                return addresses.get(0);
            }
            catch (IOException ex) {
                return null;
            }

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Address result) {
            country.setText(result.getCountryName());
            province.setText(result.getAdminArea());
            town.setText(result.getLocality());

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



    public void navigateNext(View view){
        Intent nextInt = new Intent(this, NewRecordActivity3.class);
        startActivity(nextInt);
    }
}
