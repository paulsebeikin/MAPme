package psyblaze.mapme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import Classes.Template;
import Classes.Web;

public class ProfileActivity extends AppCompatActivity {

    //region UI components
    Spinner country_spin;
    Spinner province_spin;
    Spinner project_spin;

    ArrayAdapter projAdapter;

    TextView name, email, adu;
    //endregion

    //region Objects
    ArrayAdapter spinnerAdapter;
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

        project_spin = (Spinner) findViewById(R.id.profProject);
        projAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Web.projects);
        projAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        project_spin.setAdapter(projAdapter);

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
        } else {
            template = new Template();
        }
    }
}
