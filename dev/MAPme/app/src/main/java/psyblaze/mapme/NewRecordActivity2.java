package psyblaze.mapme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class NewRecordActivity2 extends AppCompatActivity {

    TextView country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record2);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);


        country = (TextView) findViewById(R.id.country);
        String locale = this.getResources().getConfiguration().locale.getDisplayCountry();
        country.setText(locale);
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
}
