package psyblaze.mapme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ProfileActivity extends AppCompatActivity {

    Spinner country_spin;
    Spinner province_spin;
    Spinner project_spin;


    ArrayAdapter spinnerAdapter;
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


        String[] project_values = getResources().getStringArray(R.array.Projects);

        project_spin = (Spinner) findViewById(R.id.profProject);
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, project_values);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        project_spin.setAdapter(spinnerAdapter);

    }
}
