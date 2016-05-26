package psyblaze.mapme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeScreenActivity extends AppCompatActivity {

    //region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar action_bar = (Toolbar) findViewById(R.id.mapme_toolbar);
        setSupportActionBar(action_bar);
    }
    //endregion

    //region Activity Methods
    public void NewRecord(View view){
        Intent newRecInt = new Intent(this, NewRecordActivity.class);
        startActivity(newRecInt);
    }

    public void goAbout(View view){
        Intent aboutInt = new Intent(this, AboutActivity.class);
        startActivity(aboutInt);
    }

    public void openHistory(View view){
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        startActivity(historyIntent);
    }

    public void openProfile(View view) {
        Intent proIntent = new Intent(this, ProfileActivity.class);
        startActivity(proIntent);
    }

    public void openHelp(View view) {
        Intent helpInt = new Intent (this, HelpActivity.class);
        startActivity(helpInt);
    }
    //endregion

    //region Toolbar Stuff
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
            case R.id.action_logout:
                Toast.makeText(this,"Logging out...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                Intent aboutAct = new Intent(this, AboutActivity.class);
                startActivity(aboutAct);
                return true;
            case R.id.action_new_record:
                Intent newRecInt = new Intent(this, NewRecordActivity.class);
                startActivity(newRecInt);
                return true;
            case R.id.action_history:
                Intent historyInt = new Intent(this, HistoryActivity.class);
                startActivity(historyInt);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion
}
