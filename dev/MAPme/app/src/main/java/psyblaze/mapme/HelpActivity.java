package psyblaze.mapme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import Classes.Help;
import Classes.HelpArrayAdapter;
import Classes.HelpLiterals;

public class HelpActivity extends AppCompatActivity implements HelpLiterals {

    private ListView listView;
    private static final int DISPLAY_HELP = 1;
    HelpArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

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

        listView = (ListView) findViewById(R.id.listView);
        Help [] allHelp = new Help[4];
        allHelp[0] = new Help(newRecHelp, "Creating a New Record");
        allHelp[1] = new Help(logRegHelp, "How to register and log in");
        allHelp[2] = new Help(profHelp, "Using the Profile");
        allHelp[3] = new Help(historyHelp, "history help");

        arrayAdapter = new HelpArrayAdapter(this, new ArrayList(Arrays.asList(allHelp)));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(getApplicationContext(), HelpDetailsActivity.class);
                detailsIntent.putExtra(Help.class.toString(), arrayAdapter.getHelp(position));
                startActivityForResult(detailsIntent, DISPLAY_HELP);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == DISPLAY_HELP)  {
            Help updated = data.getParcelableExtra(Help.class.toString());
            arrayAdapter.updateHelp(updated);
        }
    }
}
